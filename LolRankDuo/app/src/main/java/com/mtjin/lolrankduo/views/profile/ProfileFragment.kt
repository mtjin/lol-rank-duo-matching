package com.mtjin.lolrankduo.views.profile

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModel()
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            binding.ivProfileImage.setImageURI(uri)
        }

    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            pickImage.observe(this@ProfileFragment, Observer {
                getContent.launch(Intent.ACTION_PICK)
            })
        }
    }
}