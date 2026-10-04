package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        TopicConfidenceEntity::class,
        SavedFormulaEntity::class,
        QuizHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MathEaseDatabase : RoomDatabase() {
    abstract fun mathEaseDao(): MathEaseDao

    companion object {
        @Volatile
        private var INSTANCE: MathEaseDatabase? = null

        fun getDatabase(context: Context): MathEaseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MathEaseDatabase::class.java,
                    "mathease_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
