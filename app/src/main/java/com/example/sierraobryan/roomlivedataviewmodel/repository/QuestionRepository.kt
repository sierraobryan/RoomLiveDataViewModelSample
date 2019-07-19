package com.example.sierraobryan.roomlivedataviewmodel.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.sierraobryan.roomlivedataviewmodel.dao.QuestionDao
import com.example.sierraobryan.roomlivedataviewmodel.database.QuestionRoomDatabase
import com.example.sierraobryan.roomlivedataviewmodel.model.Question

class QuestionRepository(application: Application){

        private val questionDao: QuestionDao
        private val listLiveData: LiveData<List<Question>>

        init {
            val questionRoomDatabase = QuestionRoomDatabase.getInstance(application)
            questionDao = questionRoomDatabase?.questionDao()!!
            listLiveData = questionDao.getAll()
        }

        fun getAllQuestions(): LiveData<List<Question>> {
            return listLiveData
        }

        fun insert(question: Question) {
            insertAsyncTask(questionDao).execute(question)
        }

        fun update(question: Question) {
            updateAsyncTask(questionDao).execute(question)
        }

    fun delete(question: Question) {
        deleteAsyncTask(questionDao).execute(question)
    }

        private class insertAsyncTask internal constructor(private val mAsyncTaskDao: QuestionDao) : AsyncTask<Question, Void, Void>() {

            override fun doInBackground(vararg params: Question): Void? {
                mAsyncTaskDao.insert(params[0])
                return null
            }
        }

    private class updateAsyncTask internal constructor(private val mAsyncTaskDao: QuestionDao) : AsyncTask<Question, Void, Void>() {

        override fun doInBackground(vararg params: Question): Void? {
            mAsyncTaskDao.update(params[0].question,params[0].answer)
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: QuestionDao) : AsyncTask<Question, Void, Void>() {

        override fun doInBackground(vararg params: Question): Void? {
            mAsyncTaskDao.delete(params[0].question,params[0].answer)
            return null
        }
    }

}
