package com.lysenko.myapplication.data.remote.model

import android.os.Parcel
import android.os.Parcelable

class Question : Parcelable {
    var id = 0
    var name: String? = null
    var answers: Answer? = null
    var questionAuthor: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        questionAuthor = parcel.readString()
    }

    constructor() {}

    constructor(id: Int, name: String?, answers: Answer?, questionAuthor: String?) {
        this.id = id
        this.name = name
        this.answers = answers
        this.questionAuthor = questionAuthor
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(questionAuthor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }

}