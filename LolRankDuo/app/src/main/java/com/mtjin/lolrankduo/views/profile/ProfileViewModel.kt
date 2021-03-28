package com.mtjin.lolrankduo.views.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.lolrankduo.base.BaseViewModel
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.data.profile.ProfileRepository
import com.mtjin.lolrankduo.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileViewModel(private val repository: ProfileRepository) : BaseViewModel() {
    var gameId = MutableLiveData("")
    var age = MutableLiveData("")
    private lateinit var imageUri: Uri
    var user: User = User()

    private val _pickImage: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _ageEmpty: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _gameIdEmpty: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _initUserInfo: SingleLiveEvent<Unit> = SingleLiveEvent()

    val pickImage: LiveData<Boolean> get() = _pickImage
    val ageEmpty: LiveData<Boolean> get() = _ageEmpty
    val gameIdEmpty: LiveData<Boolean> get() = _gameIdEmpty
    val initUserInfo: LiveData<Unit> get() = _initUserInfo

    fun pickImage() {
        _pickImage.call()
    }

    fun setImageUri(uri: Uri) {
        imageUri = uri
    }

    fun setUserInfo(user: User) {
        this.user = user
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
                _initUserInfo.call()
                repository.uploadProfileImage(imageUri = imageUri)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMapCompletable { imageUrl ->
                        repository.updateProfileInfo(user = user, imageUrl = imageUrl)
                    }
                    .doOnSubscribe { showLottieProgress() }
                    .doAfterTerminate { hideLottieProgress() }
                    .subscribeBy(
                        onComplete = {

                        },
                        onError = {

                        }
                    )
            }
        }
    }

}