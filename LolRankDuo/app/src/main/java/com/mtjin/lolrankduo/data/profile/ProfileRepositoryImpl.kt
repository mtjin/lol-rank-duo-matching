package com.mtjin.lolrankduo.data.profile

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.mtjin.lolrankduo.utils.UserInfo
import com.mtjin.lolrankduo.utils.constants.ERR_UPLOAD_IMAGE
import io.reactivex.rxjava3.core.Single

class ProfileRepositoryImpl(
    private val db: FirebaseFirestore,
    private val storage: StorageReference
) : ProfileRepository {
    override fun uploadProfileImage(imageUri: Uri): Single<String> {
        return Single.create { emitter ->
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
}