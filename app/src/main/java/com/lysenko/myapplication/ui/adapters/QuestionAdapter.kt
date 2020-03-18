package com.lysenko.myapplication.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
import java.util.*

interface QuestionClickHandler {
    fun onItemClick(item: Question)
}

interface ButtonClickHandler {
    fun onItemClick(item1: Question, item: String)
}

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private val questionsList: MutableList<Question> = LinkedList()
    private var questionClickHandler: QuestionClickHandler? = null
    private var buttonClickHandler: ButtonClickHandler? = null

    fun setData(newHeroes: List<Question>) {
        questionsList.clear()
        questionsList.addAll(newHeroes)
        notifyDataSetChanged()
    }

    fun attachClickHandler(questionClickHandler: QuestionClickHandler) {
        this.questionClickHandler = questionClickHandler
    }

    fun attachButtonClickHandler(buttonClickHandler: ButtonClickHandler) {
        this.buttonClickHandler = buttonClickHandler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.question_cell,
                parent, false
            ), questionClickHandler = questionClickHandler, buttonClickHandler = buttonClickHandler
        )
    }

    override fun getItemCount(): Int {
        return questionsList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = questionsList[position])
    }

    class ViewHolder(
        itemView: View, private val questionClickHandler: QuestionClickHandler?,
        private val buttonClickHandler: ButtonClickHandler?
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val questionTitle: TextView = itemView.findViewById(R.id.questionTitle)
        private val questionAuthor: TextView = itemView.findViewById(R.id.questionAuthor)
        private val questionArea: View = itemView.findViewById(R.id.questionArea)
        private val questionArea2: View = itemView.findViewById(R.id.questionArea2)
        private val questionID: TextView = itemView.findViewById(R.id.questionID)
        private val buttonAnswer1: Button = itemView.findViewById(R.id.buttonAnswer1)
        private val buttonAnswer2: Button = itemView.findViewById(R.id.buttonAnswer2)
        private val buttonAnswer3: Button = itemView.findViewById(R.id.buttonAnswer3)
        private val buttonAnswer4: Button = itemView.findViewById(R.id.buttonAnswer4)
        private val buttonAnswer5: Button = itemView.findViewById(R.id.buttonAnswer5)
        private val buttonAnswer6: Button = itemView.findViewById(R.id.buttonAnswer6)
        private val buttonAnswer7: Button = itemView.findViewById(R.id.buttonAnswer7)


        fun bind(model: Question) {
            questionArea.setOnClickListener {
                questionClickHandler?.onItemClick(item = model)
            }
            questionArea2.setOnClickListener {
                questionClickHandler?.onItemClick(item = model)
            }


            questionID.text = model.id.toString()

            questionTitle.text = model.name
            questionAuthor.text = model.questionAuthor

            val listOfAnswerButtons =
                arrayListOf(
                    buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4, buttonAnswer5,
                    buttonAnswer6, buttonAnswer7
                )

            listOfAnswerButtons.forEach {
                it.visibility = View.GONE
            }

            val listOfAnswers = model.answers!!.answerName!!

            for (i in 0 until listOfAnswers.size) {
                listOfAnswerButtons[i].text = listOfAnswers[i]
                listOfAnswerButtons[i].visibility = View.VISIBLE
                listOfAnswerButtons[i].setOnClickListener {
                    buttonClickHandler?.onItemClick(
                        item1 = model,
                        item = listOfAnswerButtons[i].text.toString()
                    )
                }
            }
        }
    }
}