package com.example.sierraobryan.roomlivedataviewmodel.database


//import android.arch.persistence.room.Database
//import android.arch.persistence.room.RoomDatabase
//import android.arch.persistence.room.Room
//import android.content.Context
//import com.example.sierraobryan.roomlivedataviewmodel.dao.NameDao
//import com.example.sierraobryan.roomlivedataviewmodel.model.Name
//
//@Database(entities = arrayOf(Name::class), version = 1)
//abstract class NameRoomDatabase : RoomDatabase() {
//
//    abstract fun nameDao(): NameDao
//
//    companion object {
//        private var INSTANCE: NameRoomDatabase? = null
//
//        fun getInstance(context: Context): NameRoomDatabase? {
//            if (INSTANCE == null) {
//                synchronized(NameRoomDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                        NameRoomDatabase::class.java, "name.db")
//                        .build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
//}

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import com.example.sierraobryan.roomlivedataviewmodel.dao.QuestionDao
import android.arch.persistence.db.SupportSQLiteDatabase
import android.os.AsyncTask
import com.example.sierraobryan.roomlivedataviewmodel.model.Question


@Database(entities = arrayOf(Question::class), version = 1,  exportSchema = false)
abstract class
QuestionRoomDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object {
        private var INSTANCE: QuestionRoomDatabase? = null

        fun getInstance(context: Context): QuestionRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(QuestionRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        QuestionRoomDatabase::class.java, "name.db"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : RoomDatabase.Callback() {

                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                                // If you want to keep the data through app restarts,
                                // comment out the following line.
                                PopulateDbAsync(INSTANCE).execute()
                            }
                        })
                        .build()
                }
            }
            return INSTANCE
        }

    }




    private class PopulateDbAsync
    internal constructor(db: QuestionRoomDatabase?) : AsyncTask<Void, Void, Void>() {

        private lateinit var mDao: QuestionDao
        internal var questions = arrayOf("Question 1", "Question 2", "Question 3")
        internal var answers = arrayOf("Answer 1", "", "Answer 3")

        init {
            if (db != null) {
                mDao = db.questionDao()
            }
        }

        override fun doInBackground(vararg params: Void): Void? {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll()

            for (i in 0..questions.size - 1) {
                val question = Question(questions[i], answers[i])
                mDao.insert(question)
            }
            return null
        }
    }
}