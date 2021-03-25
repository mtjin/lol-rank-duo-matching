package com.mtjin.lolrankduo.views.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.lolrankduo.base.BaseViewModel
import com.mtjin.lolrankduo.data.profile.ProfileRepository
import com.mtjin.lolrankduo.utils.SingleLiveEvent

class ProfileViewModel(private val repository: ProfileRepository) : BaseViewModel() {
    var gameId = MutableLiveData("")
    var age = MutableLiveData("")

    private val _pickImage: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _ageEmpty: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _gameIdEmpty: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val pickImage: LiveData<Boolean> get() = _pickImage
    val ageEmpty: LiveData<Boolean> get() = _ageEmpty
    val gameIdEmpty: LiveData<Boolean> get() = _gameIdEmpty

    fun pickImage() {
        _pickImage.call()
    }

    fun editProfile() {

        when {
            gameId.value.isNullOrBlank() -> {
                _gameIdEmpty.call()
            }
            age.value.isNullOrBlank() -> {
                _ageEmpty.call()
            }
            else -> {

            }
        }
    }

}