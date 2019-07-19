package com.example.sierraobryan.roomlivedataviewmodel.model

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "question_table")
data class Question(
    @PrimaryKey
    @ColumnInfo(name = "question")
    val question : String,
    @ColumnInfo(name = "answer")
    val answer : String
)

