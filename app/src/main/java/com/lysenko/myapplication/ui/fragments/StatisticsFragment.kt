package com.lysenko.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.lysenko.myapplication.R
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.lysenko.myapplication.App
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.helpers.Keys
import com.lysenko.myapplication.ui.adapters.AnsweredAdapter
import com.lysenko.myapplication.ui.adapters.AnsweredClickHandler
import com.lysenko.myapplication.ui.viewModels.StatisticsViewModel
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_statistics.*


class StatisticsFragment : Fragment() {

    private var questionArgs: Question? = null
    private var buttonArgs: String? = null
    private lateinit var adapter: AnsweredAdapter

    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            //тут толко Question в аргументах
            questionArgs = arguments?.getParcelable(Keys.Question.title)
            //тут название  1  выбранной кнопки в аргументах
            buttonArgs = arguments?.getString(Keys.Button.title)

            //лист ответов из аргументов
            val listOfAnswers = questionArgs!!.answers!!.answerName

            twStatisticAnswerTitle.text =
                "${resources.getString(R.string.question)}: ${questionArgs!!.name}"

            if (buttonArgs != null) {

                listOfAnswers!!.add(buttonArgs!!)
                statisticsViewModel.updateAnswers(question = questionArgs!!)

                drawDiagram(view, listOfAnswers)
                drawRecyclerList()
                archive.text = "Архив"
            } else {
                drawDiagram(view, listOfAnswers!!)
                drawRecyclerList()
                archive.text = "Архив"
            }
        } else {
            statisticsViewModel.fetchQuestions().observe(this, Observer {
                stats.text = "Всего вопросов: ${it.size}"
                var i = 0
                var j = 0
                it.forEach {
                    if (it.questionId == App.getQuestionID(it.questionId) / 4 ||
                        it.questionId == App.getQuestionID(it.questionId) / 2
                    )
                        i++
                    if (it.questionId == App.getQuestionID(it.questionId) / 4)
                        j++
                }
                stats2.text = "Вы ответили на  ${i} вопрос(ов)"
                archive.text = "В архиве ${j} вопрос(ов)"
            })

            animatedPieView.visibility = View.GONE
            drawRecyclerList()
        }
    }

    private fun drawRecyclerList() {
        statisticsViewModel =
            ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        statisticsViewModel.fetchAnswered().observe(this, Observer {
            setupAdapter()
            adapter.setData(it)
        })
    }

    private fun setupAdapter() {
        adapter = AnsweredAdapter(context!!)
        recyclerAnsweredList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerAnsweredList.adapter = adapter

        adapter.attachClickHandler(object : AnsweredClickHandler {
            override fun onItemClick(item: Question) {
                val bundle = Bundle()
                bundle.putParcelable(Keys.Question.title, item)
                animatedPieView.visibility = View.VISIBLE
                stats.visibility = View.GONE
                stats2.visibility = View.GONE
                recyclerAnsweredList.findNavController().navigate(R.id.nav_statistics, bundle)
            }
        })
    }

    private fun drawDiagram(view: View, listOfAnswers: ArrayList<String>) {

        val countMap: MutableMap<String, Int?> = HashMap()
        for (item in listOfAnswers) {
            if (countMap.containsKey(item)) {
                countMap[item] = countMap[item]?.plus(1)
            } else {
                countMap[item] = 0
            }
        }

        val listOfColors = arrayListOf(
            R.color.red, R.color.green, R.color.blueLight,
            R.color.yellow, R.color.orange, R.color.purple
        )

        val mAnimatedPieView = view.findViewById<AnimatedPieView>(R.id.animatedPieView)
        val config = AnimatedPieViewConfig()

        config.startAngle(-90f)
            .duration(500)
            .autoSize(true)
            .drawText(true)
            .textSize(45f)
            .textGravity(AnimatedPieViewConfig.ECTOPIC)

        var i = 0
        countMap.forEach { (key: String, value: Int?) ->

            config.addData(
                SimplePieInfo(
                    value!!.toDouble(), resources.getColor(listOfColors[i++]), key + ": " + value
                )
            )
        }

        mAnimatedPieView.applyConfig(config)
        mAnimatedPieView.start()
    }
}