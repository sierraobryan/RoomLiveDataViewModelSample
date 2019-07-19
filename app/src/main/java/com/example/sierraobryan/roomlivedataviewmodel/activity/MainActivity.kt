package com.example.sierraobryan.roomlivedataviewmodel.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.sierraobryan.roomlivedataviewmodel.R
import com.example.sierraobryan.roomlivedataviewmodel.adapter.QuestionRecyclerViewAdapter
import com.example.sierraobryan.roomlivedataviewmodel.model.Question
import com.example.sierraobryan.roomlivedataviewmodel.viewmodel.QuestionViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val NEW_WORD_ACTIVITY_REQUEST_CODE = 1

    private var questionViewModel: QuestionViewModel?= null
    val adapter by lazy {
        QuestionRecyclerViewAdapter(this, questionViewModel!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        questionViewModel?.allQuestions?.observe(this, object : Observer<List<Question>> {
            override fun onChanged(t: List<Question>?) {
                adapter.setQuestions(t!!)
            }
        })

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewQuestionActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val question = Question(data!!.getStringExtra(NewQuestionActivity.EXTRA_QUESTION),
                data.getStringExtra(NewQuestionActivity.EXTRA_ANSWER))
            questionViewModel?.insert(question)
        } else if (requestCode == QuestionRecyclerViewAdapter.UPDATE_QUESTION_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK) {
            val question = Question(data!!.getStringExtra(UpdateQuestionActivity.EXTRA_QUESTION),
                data.getStringExtra(UpdateQuestionActivity.EXTRA_ANSWER))
            questionViewModel?.update(question)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
