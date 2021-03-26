package com.mtjin.lolrankduo.data.profile

import android.net.Uri
import io.reactivex.rxjava3.core.Single

interface ProfileRepository {
    fun uploadProfileImage(imageUri: Uri): Single<String>
}