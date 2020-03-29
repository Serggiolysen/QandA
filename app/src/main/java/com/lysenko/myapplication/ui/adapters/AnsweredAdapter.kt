package com.lysenko.myapplication.ui.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.myapplication.App
import com.lysenko.myapplication.R
import com.lysenko.myapplication.data.remote.model.Question
import java.util.*


interface AnsweredClickHandler {
    fun onItemClick(item: Question)
}

class AnsweredAdapter(val context: Context) : RecyclerView.Adapter<AnsweredAdapter.ViewHolder>() {

    private val answeredList: MutableList<Question> = LinkedList()
    private var answeredClickHandler: AnsweredClickHandler? = null


    fun setData(list: List<Question>) {
        answeredList.clear()
        answeredList.addAll(list)
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
        return answeredList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = answeredList[position])

    }

    inner class ViewHolder(
        itemView: View, private val answeredClickHandler: AnsweredClickHandler?
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val questionTitle: TextView = itemView.findViewById(R.id.questionTitleAnswered)
        private val questionAuthor: TextView = itemView.findViewById(R.id.questionAuthorAnswered)
        private val questionArea: View = itemView.findViewById(R.id.questionAreaAnswered)
        private val buttonReAnswer: Button = itemView.findViewById(R.id.buttonReAnswerAnswered)


        fun bind(model: Question) {
            questionArea.setOnClickListener {
                answeredClickHandler?.onItemClick(item = model)
            }

            buttonReAnswer.setOnClickListener {
                answeredClickHandler?.onItemClick(item = model)
                App.changeQuestionIDx2(model.questionId)
            }

            questionTitle.text = model.name
            if (TextUtils.isEmpty(model.questionAuthor)) {
                questionAuthor.text = ""
            } else questionAuthor.text =
                "${context.resources.getString(R.string.questionAuthor)}: ${model.questionAuthor}"

        }
    }
}