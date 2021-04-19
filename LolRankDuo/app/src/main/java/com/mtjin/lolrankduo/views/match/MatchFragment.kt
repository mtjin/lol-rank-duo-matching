package com.mtjin.lolrankduo.views.match

import android.view.View
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.databinding.FragmentMatchBinding


class MatchFragment : BaseFragment<FragmentMatchBinding>(R.layout.fragment_match) {
    override fun init() {
        binding.toolbar.run {
            tbToolbar.title = getString(R.string.duo_matching_text)
            ivEdit.visibility = View.GONE
        }
    }
}