package com.lysenko.myapplication.data.remote.providers

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lysenko.myapplication.data.remote.model.Question
import io.reactivex.Observable


class FireBaseProvider {

    private var database: FirebaseDatabase =
        FirebaseDatabase.getInstance()

    private var myRef =
        database.getReference("questions")


    fun pushToFirebase(questionList: ArrayList<Question>) {
        questionList.forEach {
            println("jjjj  id = ${it.id}     question = ${it.name}   author = ${it.questionAuthor} " +
                    " answers =  ${it.answers}")
        }
        myRef.setValue(questionList)
    }

    fun updateFirebase(question: Question){
        database.setPersistenceEnabled(true)

        val firebaseListAdapter = FirebaseList
//        val childUpdates = HashMap<String, Any>()
//
//        question.answers!!.answerName!!.forEach {
//            childUpdates["answers/answerName"] = it
//        }
//        childUpdates["id"] = question.id.toString()
//        childUpdates["name"] = question.name.toString()
//        childUpdates["questionAuthor"] = question.questionAuthor.toString()

        myRef.updateChildren(childUpdates)
    }

    fun fetchFromFirebase(): Observable<MutableList<Question>> {

//        if (isThereInternetConnection()){ .... }
        val observable = Observable.create<MutableList<Question>> { emitter ->

            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val list = arrayListOf<Question>()

                    val snapshotIterable: Iterable<DataSnapshot> = dataSnapshot.children
                    val iterator = snapshotIterable.iterator()

                    while (iterator.hasNext()) {
                        val questionEntity: Question? =
                            iterator.next().getValue<Question>(Question::class.java)
                        if (questionEntity != null) {
                            list.add(questionEntity)
                        }
                    }

                    emitter.onNext(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    emitter.onError(FirebaseException(error.message));
                    Log.d("AAAA", "Failed to read value from FB", error.toException())
                }
            })

        }
//        else {emitter.onError(new NetworkConnectionException())}
        return observable
    }
}