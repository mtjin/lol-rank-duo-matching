package com.mtjin.lolrankduo.views.login

import android.util.Log
import androidx.lifecycle.LiveData
import com.kakao.auth.Session
import com.mtjin.lolrankduo.base.BaseViewModel
import com.mtjin.lolrankduo.data.login.LoginRepository
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
    private val _insertUserResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _kakaoLogin = SingleLiveEvent<Session>()

    val insertUserResult: LiveData<Boolean> get() = _insertUserResult
    val kakaoLogin: LiveData<Session> get() = _kakaoLogin

    fun insertUser(user: User) {
        compositeDisposable.add(
            loginRepository.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = { _insertUserResult.value = true },
                    onError = { _insertUserResult.value = false }
                )
        )
    }

    fun kakaoLogin() {
        Log.d("AAAAA", "카카오로그인 뷰모델 시작!!!!!!!")
        showProgress()
        _kakaoLogin.value = loginRepository.kakaoLogin()
    }
}