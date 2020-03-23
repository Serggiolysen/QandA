package com.lysenko.myapplication

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.util.*


class App : Application() {

    companion object {
        private lateinit var prefs: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        fun setQuestionID(questionID: Long) {
            editor.putLong(questionID.toString(), questionID).apply()
        }

        fun getQuestionID(questionID: Long): Long {
            return prefs.getLong(questionID.toString(), 0L)
        }

        fun changeQuestionIDx2(questionID: Long) {
            editor.putLong(questionID.toString(), questionID * 2).apply()
        }

        fun setAnswer(questionID: Long, answer: String) {
            editor.putString((questionID - 1).toString(), answer).apply()
        }

        fun getAnswer(questionID: Long): String {
            return prefs.getString((questionID - 1).toString(), "")!!
        }

        fun changeQuestionIDx4(questionID: Long) {
            editor.putLong(questionID.toString(), questionID * 4).apply()
        }
    }

    override fun onCreate() {
        super.onCreate()

        prefs = getSharedPreferences("SaredPrefsFile", Context.MODE_PRIVATE)
        editor = prefs.edit()


    }
}