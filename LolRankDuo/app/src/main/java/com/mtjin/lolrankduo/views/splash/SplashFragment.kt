package com.mtjin.lolrankduo.views.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseFragment
import com.mtjin.lolrankduo.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {


    override fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }, 1000)
    }

}