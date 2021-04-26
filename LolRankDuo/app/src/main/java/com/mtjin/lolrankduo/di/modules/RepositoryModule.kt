package com.mtjin.lolrankduo.di.modules


import com.mtjin.lolrankduo.data.login.LoginRepository
import com.mtjin.lolrankduo.data.login.LoginRepositoryImpl
import com.mtjin.lolrankduo.data.match.MatchRepository
import com.mtjin.lolrankduo.data.match.MatchRepositoryImpl
import com.mtjin.lolrankduo.data.profile.ProfileRepository
import com.mtjin.lolrankduo.data.profile.ProfileRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }
}