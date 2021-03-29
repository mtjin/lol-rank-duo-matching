package com.mtjin.lolrankduo.views.main

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mtjin.lolrankduo.R
import com.mtjin.lolrankduo.base.BaseActivity
import com.mtjin.lolrankduo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    fun initNavigation() {
        val navController = findNavController(R.id.main_nav_host)
        binding.mainBottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.bottom_nav_1 || destination.id == R.id.bottom_nav_2 || destination.id == R.id.bottom_nav_3 || destination.id == R.id.bottom_nav_4) {
                binding.mainBottomNavigation.visibility = View.VISIBLE
            } else {
                binding.mainBottomNavigation.visibility = View.GONE
            }
        }
    }
}