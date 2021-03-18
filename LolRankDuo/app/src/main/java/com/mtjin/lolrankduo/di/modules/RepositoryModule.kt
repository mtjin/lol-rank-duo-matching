package com.mtjin.lolrankduo.di.modules


import com.mtjin.lolrankduo.data.login.LoginRepository
import com.mtjin.lolrankduo.data.login.LoginRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}