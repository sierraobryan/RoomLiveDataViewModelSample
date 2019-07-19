package com.example.sierraobryan.roomlivedataviewmodel.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.sierraobryan.roomlivedataviewmodel.model.Question
import com.example.sierraobryan.roomlivedataviewmodel.repository.QuestionRepository

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

        private val questionRepository: QuestionRepository
        internal val allQuestions: LiveData<List<Question>>

        init {
            questionRepository = QuestionRepository(application)
            allQuestions = questionRepository.getAllQuestions()
        }

        fun insert(question: Question) {
            questionRepository.insert(question)
        }

        fun update(question: Question) {
            questionRepository.update(question)
        }

        fun delete(question: Question) {
            questionRepository.delete(question)
        }
}