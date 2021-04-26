package com.mtjin.lolrankduo.views.match

import android.util.Log
import androidx.lifecycle.LiveData
import com.mtjin.lolrankduo.base.BaseViewModel
import com.mtjin.lolrankduo.data.match.MatchRepository
import com.mtjin.lolrankduo.data.models.User
import com.mtjin.lolrankduo.utils.SingleLiveEvent
import com.mtjin.lolrankduo.utils.UserInfo
import com.mtjin.lolrankduo.utils.constants.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MatchViewModel(private val repository: MatchRepository) : BaseViewModel() {
    var isProfileValidate: Boolean = false

    private val _duoMatchResult: SingleLiveEvent<User> = SingleLiveEvent()
    private val _profileValidate: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _requestProfileSuccess: SingleLiveEvent<User> = SingleLiveEvent()


    val duoMatchResult: LiveData<User> get() = _duoMatchResult
    val profileValidate: LiveData<Unit> get() = _profileValidate
    val requestProfileSuccess: LiveData<User> get() = _requestProfileSuccess

    fun clickDuoMatch() {
        if (isProfileValidate) {
            repository.requestDuoMatch(UserInfo.profile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _duoMatchResult.value = it
                    },
                    onError = {
                        Log.d(TAG, "MatchViewModel requestProfile() error " + it.localizedMessage)
                    }
                )
                .addTo(compositeDisposable)
        } else {
            _profileValidate.call()
        }
    }

    fun requestProfile() {
        repository.requestProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _requestProfileSuccess.value = it
                },
                onError = {
                    Log.d(TAG, "MatchViewModel requestProfile() error " + it.localizedMessage)
                }
            )
            .addTo(compositeDisposable)
    }


}