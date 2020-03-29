package com.lysenko.myapplication.ui.fragments

import SwipeToDeleteCallback
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.myapplication.App
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.helpers.Keys
import com.lysenko.myapplication.ui.ButtonClickHandler
import com.lysenko.myapplication.ui.ButtonReVoteClickHandler
import com.lysenko.myapplication.ui.QuestionAdapter
import com.lysenko.myapplication.ui.QuestionClickHandler
import com.lysenko.myapplication.ui.viewModels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //грузим из вью модели (файрбэйз) лист из Question-ов и запихиваем в адаптер
        homeViewModel.fetchQuestions().observe(this, Observer {
            setupAdapter()
            adapter.setData(it)
        })

    }

    private fun setupAdapter() {

        adapter = QuestionAdapter(context!!)
        recyclerQuestionList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerQuestionList.adapter = adapter

        //свайп на ячейке recyclerQuestionList для удаления
        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerQuestionList.adapter as QuestionAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerQuestionList)

        // ловим клик на самом вопросе (весь вью)
        adapter.attachClickHandler(object : QuestionClickHandler {
            override fun onItemClick(item: Question) {
                val bundle = Bundle()
                bundle.putParcelable(Keys.Question.title, item)
                recyclerQuestionList.findNavController().navigate(R.id.nav_statistics, bundle)
            }
        })

        // ловим клик на баттонах ответов
        adapter.attachButtonClickHandler(object : ButtonClickHandler {
            override fun onItemClick(item1: Question, item: String) {
                val bundle = Bundle()
                bundle.putParcelable(Keys.Question.title, item1)
                bundle.putString(Keys.Button.title, item)
                recyclerQuestionList.findNavController().navigate(R.id.nav_statistics, bundle)
            }
        })

        // ловим клик на батоне "голосовать заново"
        adapter.attachReVoteClickHandler(object : ButtonReVoteClickHandler {
            override fun onItemClick(item2: Question, item: String) {
                App.setQuestionID(item2.questionId)
                Log.e("vvvv  Question --", item2.toString())
                Log.e("vvvv  answer --", item)
                adapter.listUpdate()
                homeViewModel.removeAnswer(item2, item)
            }
        })
    }
}