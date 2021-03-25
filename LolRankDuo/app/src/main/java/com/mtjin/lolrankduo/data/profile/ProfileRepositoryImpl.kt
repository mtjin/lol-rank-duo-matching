package com.mtjin.lolrankduo.data.profile

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class ProfileRepositoryImpl(
    private val db: FirebaseFirestore,
    private val storage: StorageReference
) : ProfileRepository