package com.mtjin.lolrankduo.views.profile

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModel()
    private val getContent =
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
            pickImage.observe(this@ProfileFragment, Observer {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                getContent.launch(intent)
            })
        }
    }
}