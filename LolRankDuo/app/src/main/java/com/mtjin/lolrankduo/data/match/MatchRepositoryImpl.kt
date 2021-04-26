package com.mtjin.lolrankduo.data.match

import com.mtjin.lolrankduo.data.models.User
import io.reactivex.rxjava3.core.Single

class MatchRepositoryImpl : MatchRepository {
    override fun requestDuoMatch(user : User): Single<User> {
        return Single.create { emitter ->

        }
    }
}