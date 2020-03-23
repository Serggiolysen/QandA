package com.lysenko.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.data.remote.providers.FireBaseProvider

class StatisticsViewModel : ViewModel() {

    val fireBaseProvider = FireBaseProvider()

    fun updateAnswers(question: Question) {
        fireBaseProvider.updateFirebase(question)
    }
}