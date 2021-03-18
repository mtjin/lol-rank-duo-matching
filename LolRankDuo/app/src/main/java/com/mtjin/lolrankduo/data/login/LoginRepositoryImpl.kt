package com.mtjin.lolrankduo.data.login

import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.auth.Session
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.utils.DB_USER
import io.reactivex.rxjava3.core.Completable

class LoginRepositoryImpl(private val db: FirebaseFirestore) : LoginRepository {
    override fun kakaoLogin(): Session = Session.getCurrentSession()

    override fun insertUser(user: User): Completable {
        return Completable.create { emitter ->
            db.collection(DB_USER).document(user.id)
                .set(user)
                .addOnSuccessListener {
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}