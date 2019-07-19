package com.example.sierraobryan.roomlivedataviewmodel.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.sierraobryan.roomlivedataviewmodel.R
import com.example.sierraobryan.roomlivedataviewmodel.adapter.QuestionRecyclerViewAdapter

class UpdateQuestionActivity : AppCompatActivity() {

    private var mEditAnswerView: EditText? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_question)
        val mQuestionTextView: TextView = findViewById(R.id.question_text_view)
        mEditAnswerView = findViewById(R.id.edit_answer)

        val extras = intent.extras
        if (null != extras) {
            mQuestionTextView.text = extras.getString(QuestionRecyclerViewAdapter.QUESTION_TO_BE_UPDATED)
        }
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(mEditAnswerView!!.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val question = mQuestionTextView.text.toString()
                val answer = mEditAnswerView!!.text.toString()
                replyIntent.putExtra(EXTRA_QUESTION, question)
                replyIntent.putExtra(EXTRA_ANSWER, answer)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {

        val EXTRA_QUESTION = "com.example.android.wordlistsql.QUESTION"
        val EXTRA_ANSWER = "com.example.android.wordlistsql.ANSWER"
    }

}
