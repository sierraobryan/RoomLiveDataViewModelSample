package com.example.sierraobryan.roomlivedataviewmodel.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.sierraobryan.roomlivedataviewmodel.model.Question

@Dao
interface QuestionDao {

    @Query("SELECT * from question_table")
    fun getAll(): LiveData<List<Question>>

    @Insert
    fun insert(question: Question)

    @Query("UPDATE question_table SET answer=:answer WHERE question = :question")
    fun update(question: String, answer: String)

    @Query("DELETE from question_table")
    fun deleteAll()

    @Query("DELETE from question_table where question = :question and answer=:answer")
    fun delete(question: String, answer: String)

}