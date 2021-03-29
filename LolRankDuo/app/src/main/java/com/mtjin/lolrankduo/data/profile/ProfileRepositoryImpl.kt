package com.mtjin.lolrankduo.data.profile

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.utils.DB_USER
import com.mtjin.lolrankduo.utils.UserInfo
import com.mtjin.lolrankduo.utils.constants.ERR_UPLOAD_IMAGE
import com.mtjin.lolrankduo.utils.extensions.serializeToMap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProfileRepositoryImpl(
    private val db: FirebaseFirestore,
    private val storage: StorageReference
) : ProfileRepository {
    override fun uploadProfileImage(imageUri: Uri?): Single<String> {
        return Single.create { emitter ->
            if (imageUri == null) {
                emitter.onSuccess("")
                return@create
            }
            val storageRef = storage.child("${UserInfo.uuid}.png")
            val uploadTask = storageRef.putFile(imageUri)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    emitter.onSuccess(downloadUri)
                } else {
                    emitter.onError(Throwable(ERR_UPLOAD_IMAGE))
                }
            }
        }
    }

    override fun updateProfileInfo(user: User, imageUrl: String): Completable {
        return Completable.create { emitter ->
            val userMap = user.serializeToMap()
            db.collection(DB_USER)
                .document(user.id)
                .update(userMap)
                .addOnSuccessListener {
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

}