package com.rogacewicz

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch


// klasa do kontaktu z bazą danych

@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    private class TaskDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var taskDao = database.taskDao()

                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TaskDatabase {
            //if the INSTANCE is not null, then return it
            //if it IS NULL then create the database

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                )
                    .addCallback(TaskDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }


}
