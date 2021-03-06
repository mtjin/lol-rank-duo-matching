package com.mtjin.lolrankduo.di.modules

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.koin.core.module.Module
import org.koin.dsl.module

val firebaseModule: Module = module {
    single<StorageReference> { Firebase.storage.reference }
    single<FirebaseFirestore> { Firebase.firestore }
}