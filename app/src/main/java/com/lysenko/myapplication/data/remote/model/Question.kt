package com.lysenko.myapplication.data.remote.model

import android.os.Parcel
import android.os.Parcelable

class Question : Parcelable {
    var questionId = 0L
//    var userID = 0L
    var name: String? = null
    var answers: Answer? = null
    var questionAuthor: String? = null
//    var voted: Boolean = false

    constructor(parcel: Parcel) : this() {
        questionId = parcel.readLong()
//        userID = parcel.readLong()
        name = parcel.readString()
        questionAuthor = parcel.readString()
//        voted = parcel.readBoolean()
    }

    constructor() {}

    constructor(
        id: Long,
        userID: Long,
        name: String?,
        answers: Answer?,
        questionAuthor: String?,
        voted: Boolean
    ) {
        this.questionId = id
//        this.userID = userID
        this.name = name
        this.answers = answers
        this.questionAuthor = questionAuthor
//        this.voted = voted
    }


    override fun writeToParcel(parcel: Parcel, flags: kotlin.Int) {
        parcel.writeLong(questionId)
//        parcel.writeLong(userID)
        parcel.writeString(name)
        parcel.writeString(questionAuthor)
//        parcel.writeBoolean(voted)
    }

    override fun describeContents(): kotlin.Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: kotlin.Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }

}