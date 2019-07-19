package com.example.sierraobryan.roomlivedataviewmodel.adapter

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sierraobryan.roomlivedataviewmodel.R
import com.example.sierraobryan.roomlivedataviewmodel.activity.UpdateQuestionActivity
import com.example.sierraobryan.roomlivedataviewmodel.model.Question
import com.example.sierraobryan.roomlivedataviewmodel.viewmodel.QuestionViewModel

class QuestionRecyclerViewAdapter internal constructor(
    context: Context,
    viewModel: QuestionViewModel
) : RecyclerView.Adapter<QuestionRecyclerViewAdapter.NameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context = context
    private val questionViewModel = viewModel
    private var questions = emptyList<Question>() // Cached copy of words

    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionItemView: TextView = itemView.findViewById(R.id.question_text_view)
        val answerItemView: TextView = itemView.findViewById(R.id.answer_text_view)
        val noAnswerLayout: LinearLayout = itemView.findViewById(R.id.add_answer_layout)
        val showAnswerLayout: LinearLayout = itemView.findViewById(R.id.show_answer_layout)
        val showAnswerText: TextView = itemView.findViewById(R.id.show_answer)
        val showAnswerArrow: ImageView = itemView.findViewById(R.id.answer_arrow)
        val deleteQuestion: TextView = itemView.findViewById(R.id.delete_question)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val current = questions[position]
        holder.questionItemView.text = current.question
        if (holder.showAnswerText.text == context.resources.getString(R.string.hide_answer)) {
            getAnswerView(current, holder)
        }
        holder.showAnswerLayout.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if (holder.showAnswerText.text == context.resources.getString(R.string.show_answer)) {
                    holder.showAnswerArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
                    holder.showAnswerText.text = context.resources.getString(R.string.hide_answer)
                    getAnswerView(current, holder)
                    holder.deleteQuestion.visibility = View.VISIBLE
                    holder.deleteQuestion.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(p0: View?) {
                            questionViewModel.delete(current)
                        }
                    })
                } else {
                    holder.showAnswerArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                    holder.answerItemView.visibility = View.GONE
                    holder.noAnswerLayout.visibility = View.GONE
                    holder.deleteQuestion.visibility = View.GONE
                    holder.showAnswerText.text = context.resources.getString(R.string.show_answer)
                }
            }
        })
    }

    internal fun setQuestions(questions: List<Question>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    override fun getItemCount() = questions.size

    fun getAnswerView(current: Question, holder: NameViewHolder) = if (current.answer == "") {
        holder.answerItemView.visibility = View.GONE
        holder.noAnswerLayout.visibility = View.VISIBLE
        holder.noAnswerLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(context, UpdateQuestionActivity::class.java)
                intent.putExtra(QUESTION_TO_BE_UPDATED, current.question)
                if (context is Activity) {
                    context.startActivityForResult(intent, UPDATE_QUESTION_ACTIVITY_REQUEST_CODE)
                }
            }
        })
    } else {
        holder.answerItemView.visibility = View.VISIBLE
        holder.answerItemView.text = current.answer
        holder.noAnswerLayout.visibility = View.GONE
    }

    companion object {
        val UPDATE_QUESTION_ACTIVITY_REQUEST_CODE = 2
        val QUESTION_TO_BE_UPDATED = "question"

    }
}