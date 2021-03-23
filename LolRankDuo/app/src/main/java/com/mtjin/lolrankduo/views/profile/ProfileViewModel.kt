package com.mtjin.lolrankduo.views.profile

import androidx.lifecycle.LiveData
import com.mtjin.lolrankduo.base.BaseViewModel
import com.mtjin.lolrankduo.utils.SingleLiveEvent

class ProfileViewModel : BaseViewModel() {
    private val _pickImage: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val pickImage: LiveData<Boolean> get() = _pickImage

    fun pickImage() {
        _pickImage.call()
    }

    fun editProfile() {

    }

}