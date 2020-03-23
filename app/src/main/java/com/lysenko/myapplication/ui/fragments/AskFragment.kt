package com.lysenko.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Answer
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.ui.viewModels.AskViewModel
import java.util.*

class AskFragment : Fragment() {

    private lateinit var askViewModel: AskViewModel
//    private val questionCustomAskList = ArrayList<Question>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_ask, container, false)

        askViewModel = ViewModelProviders.of(this).get(AskViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionCustomAsk = view.findViewById<EditText>(R.id.questionCastomAsk)
        val questionAuthor = view.findViewById<EditText>(R.id.questionAuthor)
        val buttonAddAnswer = view.findViewById<Button>(R.id.buttonAddAnswer)
        val buttonSend = view.findViewById<Button>(R.id.buttonSend)
        val answerCustom1 = view.findViewById<EditText>(R.id.answerCustom1)
        val answerCustom2 = view.findViewById<EditText>(R.id.answerCustom2)
        val answerCustom3 = view.findViewById<EditText>(R.id.answerCustom3)
        val answerCustom4 = view.findViewById<EditText>(R.id.answerCustom4)
        val answerCustom5 = view.findViewById<EditText>(R.id.answerCustom5)
        val answerCustom6 = view.findViewById<EditText>(R.id.answerCustom6)
        val answerCustom7 = view.findViewById<EditText>(R.id.answerCustom7)

        val listOfAnswerViews = listOf<EditText>(
            answerCustom1, answerCustom2,
            answerCustom3, answerCustom4, answerCustom5, answerCustom6, answerCustom7
        )

        for (i in 1 until listOfAnswerViews.size) {
            if (listOfAnswerViews[i].text.toString().isEmpty()) {
                listOfAnswerViews[i].visibility = View.GONE
            }
        }

        buttonAddAnswer.setOnClickListener {
            for (i in 0 until listOfAnswerViews.size - 1) {
                if (listOfAnswerViews[i].text.toString().isNotEmpty()) {
                    listOfAnswerViews[i + 1].visibility = View.VISIBLE
                    listOfAnswerViews[i + 1].requestFocus()
                } else if (listOfAnswerViews[0].text.toString().isNotEmpty()) {
                    buttonSend.visibility = View.VISIBLE
                }
            }
        }

        buttonSend.setOnClickListener {
            val list = arrayListOf<String>()

            listOfAnswerViews.forEach {
                if (it.text.toString().isNotEmpty()) {
                    list.add(it.text.toString())
                }
            }

            if (questionCustomAsk.text.toString().isNotEmpty() &&
                answerCustom1.text.toString().isNotEmpty() && answerCustom2.text.toString().isNotEmpty() &&
                answerCustom1.text.toString() != answerCustom2.text.toString()
            ) {
                askViewModel.pushQuestion(
                    Question(
                        id = Date().time, userID = 0, name = questionCustomAsk.text.toString(),
                        questionAuthor = questionAuthor.text.toString(), answers = Answer(list), voted = false
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    "Заполните поля \"Вопрос\". Минимум 2 РАЗНЫХ ответа",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }
}