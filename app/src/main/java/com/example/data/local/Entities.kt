package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic_confidence")
data class TopicConfidenceEntity(
    @PrimaryKey
    val topicId: String,
    val chapterId: String,
    val topicTitle: String,
    val confidence: String, // "NEED_HELP", "PRACTICING", "MASTERED"
    val note: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_formulas")
data class SavedFormulaEntity(
    @PrimaryKey
    val formulaId: String,
    val name: String,
    val chapterTitle: String,
    val formula: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_history")
data class QuizHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chapterName: String,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)
