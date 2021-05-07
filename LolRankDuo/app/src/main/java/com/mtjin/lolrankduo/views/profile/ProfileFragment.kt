package com.mtjin.lolrankduo.views.profile

import android.content.Intent
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.databinding.FragmentProfileBinding
import com.mtjin.lolrankduo.utils.UserInfo
import com.mtjin.lolrankduo.utils.extensions.getTimestamp
import com.mtjin.lolrankduo.utils.extensions.toRankName
import com.mtjin.lolrankduo.utils.extensions.toRankNum
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModel()

    private val getImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            binding.ivProfileImage.setImageURI(result.data?.data)
            result.data?.data?.let { viewModel.setImageUri(it) }
        }

    override fun init() {
        binding.vm = viewModel
        binding.toolbar.profileVm = viewModel
        initViewModelCallback()
        viewModel.requestProfile()
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
                val positionList = ArrayList<Int>()
                positionList.add(binding.spMyPosition1.selectedItem.toString().toRankNum())
                positionList.add(binding.spMyPosition2.selectedItem.toString().toRankNum())
                val teamPositionList = ArrayList<Int>()
                teamPositionList.add(binding.spTeamPosition1.selectedItem.toString().toRankNum())
                teamPositionList.add(binding.spTeamPosition2.selectedItem.toString().toRankNum())
                viewModel.setUserInfo(
                    User(
                        id = UserInfo.profile.id,
                        gameId = viewModel.gameId.value.toString(),
                        profileImage = UserInfo.profile.profileImage,
                        positionList = positionList,
                        sex = binding.spSex.selectedItem.toString(),
                        tear = binding.spTear.selectedItem.toString(),
                        age = viewModel.age.value.toString(),
                        introduce = binding.etIntroduce.text.toString(),
                        lastLoginTimestamp = getTimestamp(),
                        teamPositionList = teamPositionList,
                        voice = binding.spVoice.selectedItem.toString() == "가능",
                        fcm = UserInfo.profile.fcm,
                        historyIdList = UserInfo.profile.historyIdList,
                        recommend = UserInfo.profile.recommend
                    )
                )
            })

            editProfileSuccess.observe(this@ProfileFragment, { success ->
                if (!success) showToast(getString(R.string.profile_edit_fail_msg))
            })

            isLottieLoading.observe(this@ProfileFragment, { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })

            requestProfileSuccess.observe(this@ProfileFragment, { profile ->
                UserInfo.profile = profile
                binding.run {
                    Glide.with(thisContext).load(profile.profileImage).into(ivProfileImage)
                    etGameId.setText(profile.gameId)
                    etAge.setText(profile.age)
                    var adapter = ArrayAdapter.createFromResource(
                        thisContext,
                        R.array.sex,
                        android.R.layout.simple_spinner_item
                    )
                    var spinnerPosition = adapter.getPosition(profile.gameId)
                    spSex.setSelection(spinnerPosition)
                    adapter = ArrayAdapter.createFromResource(
                        thisContext,
                        R.array.tears,
                        android.R.layout.simple_spinner_item
                    )
                    spinnerPosition = adapter.getPosition(profile.tear)
                    spTear.setSelection(spinnerPosition)
                    adapter = ArrayAdapter.createFromResource(
                        thisContext,
                        R.array.positions,
                        android.R.layout.simple_spinner_item
                    )
                    spinnerPosition = adapter.getPosition(profile.positionList[0].toRankName())
                    spMyPosition1.setSelection(spinnerPosition)
                    spinnerPosition = adapter.getPosition(profile.positionList[1].toRankName())
                    spMyPosition2.setSelection(spinnerPosition)
                    spinnerPosition = adapter.getPosition(profile.teamPositionList[0].toRankName())
                    spTeamPosition1.setSelection(spinnerPosition)
                    spinnerPosition = adapter.getPosition(profile.teamPositionList[1].toRankName())
                    spTeamPosition2.setSelection(spinnerPosition)
                    adapter = ArrayAdapter.createFromResource(
                        thisContext,
                        R.array.voices,
                        android.R.layout.simple_spinner_item
                    )
                    val voice: String = if (profile.voice) {
                        "가능"
                    } else {
                        "불가능"
                    }
                    spinnerPosition = adapter.getPosition(voice)
                    spVoice.setSelection(spinnerPosition)
                    etIntroduce.setText(profile.introduce)
                }
            })
        }
    }
}