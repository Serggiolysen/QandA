package com.lysenko.myapplication.data.remote.providers

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lysenko.myapplication.data.remote.model.Question
import io.reactivex.Observable
import kotlin.collections.HashMap


class FireBaseProvider {

    private var database: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://qanda-9c058.firebaseio.com/")

    private var myRef = database.reference

    fun pushToFirebase(question: Question) {
        val childUpdates = HashMap<String, Any>()
        val currentDate = question.questionId
        childUpdates["$currentDate"] = question
        myRef.updateChildren(childUpdates)
    }

    fun updateFirebase(question: Question) {
        myRef.child(question.questionId.toString()).setValue(question)
    }

    fun removeEntityFirebase(question: Question) {
        myRef.child(question.questionId.toString()).removeValue()
    }

    fun removeValueFirebase(question: Question, answer: String) {
        val answersList = question.answers!!.answerName!!
        for (i in answersList.size-1 downTo 0) {
//            Log.e("pppp  list[i] --",answersList[i] )
//            Log.e("pppp  answer --",answer )
//            Log.e("pppp","_________" )


            if (answersList[i] == answer) {
                myRef.child(question.questionId.toString()).child("answers")
                    .child("answerName").child("$i").removeValue()
                break
            }
        }
    }

    fun fetchFromFirebase(): Observable<MutableList<Question>> {

//        if (isThereInternetConnection()){ .... }
        val observable = Observable.create<MutableList<Question>> { emitter ->

            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val listQuestion = arrayListOf<Question>()
                    val snapshotIterable: Iterable<DataSnapshot> = dataSnapshot.children

                    snapshotIterable.forEach {
                        val questionEntity = it.getValue(Question::class.java)
                        if (questionEntity != null) {
                            listQuestion.add(questionEntity)
                        }
                    }
                    emitter.onNext(listQuestion)
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(FirebaseException(error.message));
                    Log.d("AAAA", "Failed to read value from FB", error.toException())
                }
            })
        }
//        else {emitter.onError(new NetworkConnectionException())}
        return observable
    }
}