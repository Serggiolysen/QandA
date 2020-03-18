package com.lysenko.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lysenko.infoapp.helpers.Keys
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
import com.lysenko.myapplication.ui.ButtonClickHandler
import com.lysenko.myapplication.ui.QuestionAdapter
import com.lysenko.myapplication.ui.QuestionClickHandler
import com.lysenko.myapplication.ui.viewModels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

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
        homeViewModel.pullQuestions().observe(this, Observer {
            setupAdapter()
            adapter.setData(it)
        })

    }

    private fun setupAdapter() {

        adapter = QuestionAdapter()
        recyclerPlayersList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerPlayersList.adapter = adapter

        adapter.attachClickHandler(object : QuestionClickHandler {
            override fun onItemClick(item: Question) {
                val bundle = Bundle()
                bundle.putParcelable(Keys.Question.title, item)
                recyclerPlayersList.findNavController().navigate(R.id.nav_statistics, bundle)
            }
        })

        adapter.attachButtonClickHandler(object : ButtonClickHandler {
            override fun onItemClick(item1: Question, item: String) {
                val bundle = Bundle()
                bundle.putParcelable(Keys.Question.title, item1)
                bundle.putString(Keys.Button.title, item)
                recyclerPlayersList.findNavController().navigate(R.id.nav_statistics, bundle)
            }
        })
    }


}