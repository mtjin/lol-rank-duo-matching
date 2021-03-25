package com.mtjin.lolrankduo.views.profile

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModel()
    private val getImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            binding.ivProfileImage.setImageURI(result.data?.data)
        }

    override fun init() {
        binding.vm = viewModel
        binding.toolbar.profileVm = viewModel
        initViewModelCallback()
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
        }
    }
}