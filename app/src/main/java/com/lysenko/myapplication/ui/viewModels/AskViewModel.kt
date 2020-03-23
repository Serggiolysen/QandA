package com.lysenko.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.data.remote.providers.FireBaseProvider

class AskViewModel() : ViewModel() {

    private val fireBaseProvider = FireBaseProvider()

    fun pushQuestion(question: Question) {
        fireBaseProvider.pushToFirebase(question = question)
    }
}