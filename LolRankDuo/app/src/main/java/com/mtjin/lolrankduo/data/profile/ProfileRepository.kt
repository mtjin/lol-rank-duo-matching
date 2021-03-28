package com.mtjin.lolrankduo.data.profile

import android.net.Uri
import com.mtjin.lolrankduo.data.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProfileRepository {
    fun uploadProfileImage(imageUri: Uri?): Single<String>
    fun updateProfileInfo(user: User, imageUrl : String): Completable
}