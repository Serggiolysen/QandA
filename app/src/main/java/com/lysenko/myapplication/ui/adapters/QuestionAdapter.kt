package com.lysenko.myapplication.ui

import android.app.ActionBar
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.myapplication.App
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
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

class QuestionAdapter(val context: Context) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private val questionsList: MutableList<Question> = LinkedList()
    private var questionClickHandler: QuestionClickHandler? = null
    private var buttonClickHandler: ButtonClickHandler? = null
    private var buttonReVoteClickHandler: ButtonReVoteClickHandler? = null

    fun setData(list: List<Question>) {
        questionsList.clear()
        questionsList.addAll(list)
        questionsList.reverse()
        notifyDataSetChanged()
    }

    fun listUpdate() {
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        App.changeQuestionIDx6(questionsList[position].questionId)
        questionsList.removeAt(position)
        notifyItemRemoved(position)
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

        private val questionArea: View = itemView.findViewById(R.id.questionArea)
        private val questionTitle: TextView = itemView.findViewById(R.id.questionTitle)
        private val buttonsContainer: View = itemView.findViewById(R.id.buttonsContainer)
        private val view0: View = itemView.findViewById(R.id.view0)
        private val view1: View = itemView.findViewById(R.id.view1)
        private val view2: View = itemView.findViewById(R.id.view2)
        private val view3: View = itemView.findViewById(R.id.view3)
        private val view4: View = itemView.findViewById(R.id.view4)
        private val view5: View = itemView.findViewById(R.id.view5)
        private val view6: View = itemView.findViewById(R.id.view6)
        private val view7: View = itemView.findViewById(R.id.view7)
        private val view8: View = itemView.findViewById(R.id.view8)
        private val view9: View = itemView.findViewById(R.id.view9)
        private val buttonAnswer1: Button = itemView.findViewById(R.id.buttonAnswer1)
        private val buttonAnswer2: Button = itemView.findViewById(R.id.buttonAnswer2)
        private val buttonAnswer3: Button = itemView.findViewById(R.id.buttonAnswer3)
        private val buttonAnswer4: Button = itemView.findViewById(R.id.buttonAnswer4)
        private val buttonAnswer5: Button = itemView.findViewById(R.id.buttonAnswer5)
        private val buttonAnswer6: Button = itemView.findViewById(R.id.buttonAnswer6)
        private val buttonAnswer7: Button = itemView.findViewById(R.id.buttonAnswer7)
        private val buttonReAnswer: Button = itemView.findViewById(R.id.buttonReAnswer)
        private val buttonArchiveQuestion: Button = itemView.findViewById(R.id.archiveQuestionImg)
        private val questionAuthor: TextView = itemView.findViewById(R.id.questionAuthor)

        fun bind(model: Question) {
            // x2 - это значит вопрос отвечен
            // x4 - это значит вопрос архивирован (появится в ресайклере в статистик фрагменте)
            // x6 - это значит вопрос удален (нигде не появится)

            val listOfAnswerButtons =
                arrayListOf(
                    buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4, buttonAnswer5,
                    buttonAnswer6, buttonAnswer7, buttonReAnswer, buttonArchiveQuestion
                )

            val listOfViews =
                arrayListOf(view1, view2, view3, view4, view5, view6, view7, view8, view9)

            listOfAnswerButtons.forEach {
                it.visibility = View.GONE
            }

            listOfViews.forEach {
                it.visibility = View.GONE
            }

            if (model.questionId == App.getQuestionID(model.questionId) / 4 ||
                model.questionId == App.getQuestionID(model.questionId) / 6
            ) {
                (questionArea.layoutParams as RecyclerView.LayoutParams).setMargins(0, 0, 0, 0)
                questionArea.visibility = View.GONE
                questionTitle.visibility = View.GONE
                questionAuthor.visibility = View.GONE
                buttonsContainer.visibility = View.GONE
                view0.visibility = View.GONE
            }

            questionArea.setOnClickListener {
                questionClickHandler?.onItemClick(item = model)
            }

            questionTitle.text = model.name
            if (TextUtils.isEmpty(model.questionAuthor)) {
                questionAuthor.text = ""
            } else questionAuthor.text =
                "${context.resources.getString(R.string.questionAuthor)}: ${model.questionAuthor}"

            val setOfAnswers = model.answers!!.answerName!!.toSet()
            val listOfAnswers = setOfAnswers.toList()

            for (i in listOfAnswers.indices) {

                //если вопрос отвечен шаредпреференс вернет тру
                //т.о. активируем  кнопку  голосовать заново
                if (model.questionId == App.getQuestionID(model.questionId) / 2) {

                    listOfAnswerButtons[i].text = listOfAnswers[i]
                    buttonReAnswer.visibility = View.VISIBLE
                    buttonArchiveQuestion.visibility = View.VISIBLE
                    view8.visibility = View.VISIBLE

                    buttonReAnswer.setOnClickListener {
                        buttonReVoteClickHandler.onItemClick(
                            item2 = model,
                            item = App.getAnswer(model.questionId)
                        )
                    }
                    //иначе - если вопрос не отвечен - то просто выводим в энитити каждго квэсчена
                    //лист из кнопок с ответами
                    //клик на кнопке передает ее название (ответ) в бандл (см хоум фрагмент)
                } else {
                    listOfAnswerButtons[i].text = listOfAnswers[i]
                    listOfAnswerButtons[i].visibility = View.VISIBLE
                    listOfViews[i].visibility = View.VISIBLE

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

                //назначаем кнопке "архивировать" клик,
                //потом удаляем эту энтити, чтобы не отбражалась и делаем отметку в шаредпреференс
                buttonArchiveQuestion.setOnClickListener {
                    questionsList.remove(model)
                    App.changeQuestionIDx4(model.questionId)
                    listUpdate()
                }

            }
        }
    }
}