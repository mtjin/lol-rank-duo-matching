package com.mtjin.lolrankduo.data.match

import com.mtjin.lolrankduo.data.models.User
import io.reactivex.rxjava3.core.Single

interface MatchRepository {
    fun requestDuoMatch(user: User): Single<User>
    fun requestProfile() : Single<User>
}