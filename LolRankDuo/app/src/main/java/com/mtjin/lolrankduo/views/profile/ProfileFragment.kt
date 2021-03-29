package com.mtjin.lolrankduo.views.profile

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.databinding.FragmentProfileBinding
import com.mtjin.lolrankduo.utils.UserInfo
import com.mtjin.lolrankduo.utils.extensions.getTimestamp
import com.mtjin.lolrankduo.views.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModel()
    private val sageArgs: ProfileFragmentArgs by navArgs() // 0:로그인, 1:나머지

    private val getImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            binding.ivProfileImage.setImageURI(result.data?.data)
            result.data?.data?.let { viewModel.setImageUri(it) }
        }

    override fun init() {
        binding.vm = viewModel
        binding.toolbar.profileVm = viewModel
        initViewModelCallback()
        initNavigation()
    }

    private fun initNavigation() {
        if (sageArgs.fromWhere == 0) {
            findNavController().graph.startDestination = R.id.bottom_nav_1
            (activity as MainActivity).initNavigation()
        }
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            pickImage.observe(this@ProfileFragment, {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                getImage.launch(intent)
            })

            gameIdEmpty.observe(this@ProfileFragment, {
                showToast("게임 아이디를 적어주세요")
            })

            ageEmpty.observe(this@ProfileFragment, {
                showToast("나이를 적어주세요")
            })

            initUserInfo.observe(this@ProfileFragment, {
                val positionList = ArrayList<String>()
                positionList.add(binding.spMyPosition1.selectedItem.toString())
                positionList.add(binding.spMyPosition2.selectedItem.toString())
                val teamPositionList = ArrayList<String>()
                teamPositionList.add(binding.spTeamPosition1.selectedItem.toString())
                teamPositionList.add(binding.spTeamPosition2.selectedItem.toString())
                viewModel.setUserInfo(
                    User(
                        id = UserInfo.uuid,
                        gameId = viewModel.gameId.value.toString(),
                        profileImage = UserInfo.profileImage,
                        positionList = positionList,
                        sex = binding.spSex.selectedItem.toString(),
                        tear = binding.spTear.selectedItem.toString(),
                        age = viewModel.age.value.toString(),
                        introduce = binding.etIntroduce.text.toString(),
                        lastLoginTimestamp = getTimestamp(),
                        teamPositionList = teamPositionList,
                        voice = binding.spVoice.selectedItem.toString() == "가능",
                        fcm = UserInfo.fcm,
                        historyIdList = UserInfo.historyIdList,
                        recommend = UserInfo.recommend
                    )
                )
            })

            editProfileSuccess.observe(this@ProfileFragment, {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToMatchFragment()
                )
            })

            isLottieLoading.observe(this@ProfileFragment, { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })
        }
    }
}