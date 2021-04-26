package com.mtjin.lolrankduo.di.modules

import com.mtjin.lolrankduo.views.login.LoginViewModel
import com.mtjin.lolrankduo.views.main.MainSharedViewModel
import com.mtjin.lolrankduo.views.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MainSharedViewModel() }
}