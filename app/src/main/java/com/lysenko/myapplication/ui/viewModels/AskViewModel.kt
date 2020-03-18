package com.lysenko.myapplication.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.data.remote.providers.FireBaseProvider

class AskViewModel : ViewModel() {

    private val fireBaseProvider = FireBaseProvider()

    fun pushQuestion(questionList: ArrayList<Question>) {
        fireBaseProvider.pushToFirebase(questionList = questionList)
    }
}