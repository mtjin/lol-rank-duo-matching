package com.mtjin.lolrankduo.data.match

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.utils.UserInfo
import com.mtjin.lolrankduo.utils.constants.DB_USER
import com.mtjin.lolrankduo.utils.constants.TAG
import io.reactivex.rxjava3.core.Single

class MatchRepositoryImpl(private val db: FirebaseFirestore) : MatchRepository {
    override fun requestDuoMatch(user: User): Single<User> {
        return Single.create { emitter ->

        }
    }

    override fun requestProfile(): Single<User> {
        return Single.create { emitter ->
            db.collection(DB_USER)
                .document(UserInfo.profile.id)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: ${snapshot.data}")
                        emitter.onSuccess(snapshot.toObject(User::class.java))
                    } else {
                        Log.d(TAG, "Current data: null")
                    }
                }
        }
    }
}