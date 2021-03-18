package com.mtjin.lolrankduo.data.login

import com.kakao.auth.Session
import com.mtjin.lolrankduo.data.models.User
import io.reactivex.rxjava3.core.Completable

interface LoginRepository {
    fun kakaoLogin(): Session
    fun insertUser(user: User): Completable
}