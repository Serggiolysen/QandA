package com.lysenko.myapplication.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.myapplication.App
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.helpers.Answered
import java.util.*


interface AnsweredClickHandler {
    fun onItemClick(item: Answered)
}

class AnsweredAdapter : RecyclerView.Adapter<AnsweredAdapter.ViewHolder>() {

    private val questionsList: MutableList<Answered> = LinkedList()
    private var answeredClickHandler: AnsweredClickHandler? = null


    fun setData(list: List<Answered>) {
        questionsList.clear()
        questionsList.addAll(list)
        notifyDataSetChanged()
    }

    fun attachClickHandler(answeredClickHandler: AnsweredClickHandler) {
        this.answeredClickHandler = answeredClickHandler
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.answered_cell,
                parent, false
            ), answeredClickHandler = answeredClickHandler
        )
    }

    override fun getItemCount(): Int {
        return questionsList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = questionsList[position])

    }

    inner class ViewHolder(
        itemView: View, private val answeredClickHandler: AnsweredClickHandler?
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val questionTitle: TextView = itemView.findViewById(R.id.questionTitleAnswered)
        private val questionAuthor: TextView = itemView.findViewById(R.id.questionAuthorAnswered)
        private val questionArea: View = itemView.findViewById(R.id.questionAreaAnswered)
        private val buttonReAnswer: Button = itemView.findViewById(R.id.buttonReAnswerAnswered)


        fun bind(model: Answered) {
            questionArea.setOnClickListener {
                answeredClickHandler?.onItemClick(item = model)
            }

            questionTitle.text = model.name
            questionAuthor.text = model.questionAuthor

        }
    }
}