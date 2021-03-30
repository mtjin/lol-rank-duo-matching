package com.mtjin.lolrankduo.views.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.databinding.FragmentLoginBinding
import com.mtjin.lolrankduo.utils.UserInfo
import com.mtjin.lolrankduo.utils.extensions.getTimestamp
import com.mtjin.lolrankduo.views.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var auth: FirebaseAuth

    // 세션 콜백 구현
    private val sessionCallback: ISessionCallback = object : ISessionCallback {
        override fun onSessionOpened() {
            Log.i(TAG, "sessionCallback onSessionOpened()")
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.e(TAG, "sessionCallback onSessionOpenFailed()", exception)
        }
    }

    private fun initNavigation() {
        findNavController().graph.startDestination = R.id.matchFragment
        (activity as MainActivity).initNavigation()
    }

    override fun init() {
        binding.vm = viewModel
        //FCM 토큰 세팅
        initFcmToken()
        Session.getCurrentSession().addCallback(sessionCallback)
        initViewModelCallback()
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            kakaoLogin.observe(this@LoginFragment, Observer {
                kakaoLogin.value?.addCallback(SessionCallback())
                kakaoLogin.value?.open(AuthType.KAKAO_LOGIN_ALL, this@LoginFragment)
            })

            insertUserResult.observe(this@LoginFragment, Observer {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToMatchFragment()
                )
            })
        }
    }

    fun googleAuth(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) { //기존 아이디 로그인
                    UserInfo.uuid = auth.currentUser.uid
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToMatchFragment()
                    )
                    //viewModel.updateFCM()
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(requireActivity()) { task ->
                                        if (task.isSuccessful) {  //새 유저 로그인
                                            UserInfo.uuid = auth.currentUser.uid.toString()
                                            viewModel.insertUser(
                                                User(
                                                    id = UserInfo.uuid,
                                                    fcm = UserInfo.fcm,
                                                    lastLoginTimestamp = getTimestamp()
                                                )
                                            )
                                        } else {
                                            showToast(getString(R.string.login_fail_msg))
                                            viewModel.hideProgress()
                                        }
                                    }
                            } else {
                                showToast(getString(R.string.login_fail_msg))
                                viewModel.hideProgress()
                            }
                        }
                }
            }
    }

    inner class SessionCallback : ISessionCallback {
        // 로그인에 성공한 상태
        override fun onSessionOpened() {
            requestMe()
        }

        // 로그인에 실패한 상태
        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.e(TAG, "onSessionOpenFailed : " + exception.message)
        }

        // 사용자 정보 요청
        private fun requestMe() {
            UserManagement.getInstance()
                .me(object : MeV2ResponseCallback() {
                    override fun onSessionClosed(errorResult: ErrorResult) {
                        Log.e(TAG, "세션이 닫혀 있음: $errorResult")
                    }

                    override fun onFailure(errorResult: ErrorResult) {
                        Log.e(TAG, "사용자 정보 요청 실패: $errorResult")
                    }

                    override fun onSuccess(result: MeV2Response) {
                        Log.i(TAG, "사용자 아이디: " + result.id)
                        val email: String = "" + result.id + "@lolrankduo.com"
                        val password: String = "111111"
                        //구글이메일 로그인
                        googleAuth(email, password)
                    }
                })
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            UserInfo.fcm = task.result.toString()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }


    companion object {
        const val TAG: String = "로그인프래그먼트"
    }

}