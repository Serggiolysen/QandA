package com.lysenko.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.lysenko.infoapp.helpers.Keys
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.ui.viewModels.StatisticsViewModel
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo


class StatisticsFragment : Fragment() {

    private var questionArgs: Question? = null
    private var buttonArgs: String? = null

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
            questionArgs = arguments?.getParcelable(Keys.Question.title)
            buttonArgs = arguments?.getString(Keys.Button.title)



            val listOfAnswers = questionArgs!!.answers!!.answerName
            val question =questionArgs!!
            listOfAnswers!!.add(buttonArgs!!)

            statisticsViewModel.updateAnswers(question)

            val countMap: MutableMap<String, Int?> = HashMap()
            for (item in listOfAnswers) {
                if (countMap.containsKey(item)) {
                    countMap[item] = countMap[item]?.plus(1)
                } else {
                    countMap[item] = 1
                }
            }

            Log.d("ssss", countMap.toString())

            val listOfColors = arrayListOf(
                R.color.red, R.color.green, R.color.blueLight,
                R.color.yellow, R.color.orange, R.color.purple
            )

            val mAnimatedPieView = view.findViewById<AnimatedPieView>(R.id.animatedPieView)
            val config = AnimatedPieViewConfig()

            config.startAngle(-90f)
                .duration(500)
                .pieRadius(200f)
                .drawText(true)
                .textSize(45f)

            var i = 0
            countMap.forEach { (key: String, value: Int?) ->
                config.addData(
                    SimplePieInfo(
                        value!!.toDouble(), resources.getColor(listOfColors[i++]), key
                    )
                )
            }

            mAnimatedPieView.applyConfig(config)
            mAnimatedPieView.start()

        }
    }
}