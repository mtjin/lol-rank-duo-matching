package com.mtjin.lolrankduo.di.modules

import com.mtjin.lolrankduo.views.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { LoginViewModel(get()) }
}