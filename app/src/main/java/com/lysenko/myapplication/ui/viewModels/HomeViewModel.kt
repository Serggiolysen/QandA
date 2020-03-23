package com.lysenko.myapplication.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.data.remote.providers.FireBaseProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val fireBaseProvider = FireBaseProvider()

    fun fetchQuestions(): LiveData<MutableList<Question>> {

        val liveData2 =
            MutableLiveData<MutableList<Question>>().apply {

                fireBaseProvider.fetchFromFirebase()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        value = it
                    }
            }
        return liveData2
    }

    fun removeAnswer(question: Question, answer :String) {
        fireBaseProvider.removeValueFirebase(question, answer)
    }
}