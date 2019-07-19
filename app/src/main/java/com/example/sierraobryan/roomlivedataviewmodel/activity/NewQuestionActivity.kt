package com.example.sierraobryan.roomlivedataviewmodel.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.sierraobryan.roomlivedataviewmodel.R

class NewQuestionActivity : AppCompatActivity() {

    private var mEditQuestionView: EditText? = null
    private var mEditAnswerView: EditText? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_name)
        mEditQuestionView = findViewById(R.id.edit_question)
        mEditAnswerView = findViewById(R.id.edit_answer)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(mEditQuestionView!!.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val question = mEditQuestionView!!.text.toString()
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