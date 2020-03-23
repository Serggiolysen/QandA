package com.lysenko.myapplication.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.myapplication.App
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.helpers.Answered
import java.util.*

interface QuestionClickHandler {
    fun onItemClick(item: Question)
}

interface ButtonClickHandler {
    fun onItemClick(item1: Question, item: String)
}

interface ButtonReVoteClickHandler {
    fun onItemClick(item2: Question, item: String)
}

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private val questionsList: MutableList<Question> = LinkedList()
    private var questionClickHandler: QuestionClickHandler? = null
    private var buttonClickHandler: ButtonClickHandler? = null
    private var buttonReVoteClickHandler: ButtonReVoteClickHandler? = null

    fun setData(list: List<Question>) {
        questionsList.clear()
        questionsList.addAll(list)
        notifyDataSetChanged()
    }

    fun listUpdate() {
        notifyDataSetChanged()
    }

    fun attachClickHandler(questionClickHandler: QuestionClickHandler) {
        this.questionClickHandler = questionClickHandler
    }

    fun attachButtonClickHandler(buttonClickHandler: ButtonClickHandler) {
        this.buttonClickHandler = buttonClickHandler
    }

    fun attachReVoteClickHandler(buttonReVoteClickHandler: ButtonReVoteClickHandler) {
        this.buttonReVoteClickHandler = buttonReVoteClickHandler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.question_cell,
                parent, false
            ), questionClickHandler = questionClickHandler, buttonClickHandler = buttonClickHandler,
            buttonReVoteClickHandler = buttonReVoteClickHandler!!
        )
    }

    override fun getItemCount(): Int {
        return questionsList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = questionsList[position])

    }

    inner class ViewHolder(
        itemView: View, private val questionClickHandler: QuestionClickHandler?,
        private val buttonClickHandler: ButtonClickHandler?,
        private val buttonReVoteClickHandler: ButtonReVoteClickHandler
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val questionTitle: TextView = itemView.findViewById(R.id.questionTitle)
        private val questionAuthor: TextView = itemView.findViewById(R.id.questionAuthor)
        private val questionArea: View = itemView.findViewById(R.id.questionArea)
        private val questionArea2: View = itemView.findViewById(R.id.questionArea2)
        private val archiveQuestionImg: ImageView = itemView.findViewById(R.id.archiveQuestionImg)
        private val buttonAnswer1: Button = itemView.findViewById(R.id.buttonAnswer1)
        private val buttonAnswer2: Button = itemView.findViewById(R.id.buttonAnswer2)
        private val buttonAnswer3: Button = itemView.findViewById(R.id.buttonAnswer3)
        private val buttonAnswer4: Button = itemView.findViewById(R.id.buttonAnswer4)
        private val buttonAnswer5: Button = itemView.findViewById(R.id.buttonAnswer5)
        private val buttonAnswer6: Button = itemView.findViewById(R.id.buttonAnswer6)
        private val buttonAnswer7: Button = itemView.findViewById(R.id.buttonAnswer7)
        private val buttonReAnswer: Button = itemView.findViewById(R.id.buttonReAnswer)


        fun bind(model: Question) {
            if (model.questionId ==  App.getQuestionID(model.questionId)/4){
                questionTitle.visibility = View.GONE
                questionAuthor.visibility = View.GONE
                questionArea.visibility = View.GONE
                questionArea2.visibility = View.GONE
                archiveQuestionImg.visibility = View.GONE
                buttonAnswer1.visibility = View.GONE
                buttonAnswer2.visibility = View.GONE
                buttonAnswer3.visibility = View.GONE
                buttonAnswer4.visibility = View.GONE
                buttonAnswer5.visibility = View.GONE
                buttonAnswer6.visibility = View.GONE
                buttonAnswer7.visibility = View.GONE
                buttonReAnswer.visibility = View.GONE
            }

            questionArea.setOnClickListener {
                questionClickHandler?.onItemClick(item = model)
            }
            questionArea2.setOnClickListener {
                questionClickHandler?.onItemClick(item = model)
            }

            archiveQuestionImg.setOnClickListener {
                Answered(
                    questionId = model.questionId, questionAuthor = model.questionAuthor,
                    name = model.name, answers = model.answers
                )
                questionsList.remove(model)
                App.changeQuestionIDx4(model.questionId)
                listUpdate()
            }

            questionTitle.text = model.name
            questionAuthor.text = model.questionAuthor

            val listOfAnswerButtons =
                arrayListOf(
                    buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4, buttonAnswer5,
                    buttonAnswer6, buttonAnswer7, buttonReAnswer
                )

            listOfAnswerButtons.forEach {
                it.visibility = View.GONE
            }


            val setOfAnswers = model.answers!!.answerName!!.toSet()
            val listOfAnswers = setOfAnswers.toList()

            for (i in listOfAnswers.indices) {

                if (model.questionId == App.getQuestionID(model.questionId) / 2) {
                    listOfAnswerButtons[i].text = listOfAnswers[i]
                    Log.e("bbbb  App.getAnswer --", App.getAnswer(model.questionId))

                    buttonReAnswer.visibility = View.VISIBLE
                    buttonReAnswer.setOnClickListener {
                        buttonReVoteClickHandler.onItemClick(
                            item2 = model,
                            item = App.getAnswer(model.questionId)
                        )
                    }

                } else {
                    listOfAnswerButtons[i].text = listOfAnswers[i]
                    listOfAnswerButtons[i].visibility = View.VISIBLE
                    listOfAnswerButtons[i].setOnClickListener {
                        buttonClickHandler?.onItemClick(
                            item1 = model,
                            //это передается название кнопки/ответа в хоум фрагменте и далее через бандл
                            //надо сохранить в шаред
                            item = listOfAnswerButtons[i].text.toString()
                        )
                        App.setAnswer(model.questionId, listOfAnswerButtons[i].text.toString())
                        App.changeQuestionIDx2(model.questionId)
                    }
                }
            }
        }
    }
}