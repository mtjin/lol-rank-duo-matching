package com.mtjin.lolrankduo.views.match

import android.view.View
import androidx.fragment.app.viewModels
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.databinding.FragmentMatchBinding
import com.mtjin.lolrankduo.views.main.MainActivity


class MatchFragment : BaseFragment<FragmentMatchBinding>(R.layout.fragment_match) {

    private val viewModel: MatchViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
        initToolbar()
        initViewModelCallback()
        if ((requireActivity() as MainActivity).sharedViewModel.isValidateProfile) {
            viewModel.requestProfile()
        }
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            requestProfileSuccess.observe(this@MatchFragment, {
                (requireActivity() as MainActivity).sharedViewModel.isValidateProfile = true
                viewModel.isProfileValidate = true
            })

            profileValidate.observe(this@MatchFragment, {
                showToast("사용자님의 프로필을 먼저 완성해주세요 :)")
            })

            duoMatchResult.observe(this@MatchFragment, {

            })
        }
    }

    private fun initToolbar() {
        binding.toolbar.run {
            tbToolbar.title = getString(R.string.duo_matching_text)
            ivEdit.visibility = View.GONE
        }
    }
}