package com.lysenko.myapplication.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lysenko.myapplication.App
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.data.remote.providers.FireBaseProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StatisticsViewModel : ViewModel() {

    val fireBaseProvider = FireBaseProvider()

    fun updateAnswers(question: Question) {
        fireBaseProvider.updateFirebase(question)
    }

    fun fetchAnswered(): LiveData<MutableList<Question>> {

        val liveData2 =
            MutableLiveData<MutableList<Question>>().apply {

                fireBaseProvider.fetchFromFirebase()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val list = mutableListOf<Question>()
                        it.forEach {
                            if (it.questionId == App.getQuestionID(it.questionId) / 4){
                                list.add(it)
                            }
                        }
                        value = list
                    }
            }
        return liveData2
    }

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
}