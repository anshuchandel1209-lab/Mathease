package com.example.data.repository

import com.example.data.local.MathEaseDao
import com.example.data.local.QuizHistoryEntity
import com.example.data.local.SavedFormulaEntity
import com.example.data.local.TopicConfidenceEntity
import com.example.model.Formula
import kotlinx.coroutines.flow.Flow

class MathEaseRepository(private val dao: MathEaseDao) {

    val allTopicConfidences: Flow<List<TopicConfidenceEntity>> = dao.getAllTopicConfidences()
    val allSavedFormulas: Flow<List<SavedFormulaEntity>> = dao.getAllSavedFormulas()
    val quizHistory: Flow<List<QuizHistoryEntity>> = dao.getQuizHistory()

    suspend fun setTopicConfidence(
        topicId: String,
        chapterId: String,
        topicTitle: String,
        confidence: String
    ) {
        val existing = dao.getTopicConfidence(topicId)
        val note = existing?.note ?: ""
        dao.upsertTopicConfidence(
            TopicConfidenceEntity(
                topicId = topicId,
                chapterId = chapterId,
                topicTitle = topicTitle,
                confidence = confidence,
                note = note,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun saveTopicNote(topicId: String, chapterId: String, topicTitle: String, note: String) {
        val existing = dao.getTopicConfidence(topicId)
        if (existing != null) {
            dao.updateTopicNote(topicId, note)
        } else {
            dao.upsertTopicConfidence(
                TopicConfidenceEntity(
                    topicId = topicId,
                    chapterId = chapterId,
                    topicTitle = topicTitle,
                    confidence = "NEED_HELP",
                    note = note
                )
            )
        }
    }

    suspend fun toggleSaveFormula(formula: Formula, isCurrentlySaved: Boolean) {
        if (isCurrentlySaved) {
            dao.removeSavedFormula(formula.id)
        } else {
            dao.saveFormula(
                SavedFormulaEntity(
                    formulaId = formula.id,
                    name = formula.name,
                    chapterTitle = formula.chapterTitle,
                    formula = formula.formula
                )
            )
        }
    }

    suspend fun recordQuizScore(chapterName: String, score: Int, total: Int) {
        dao.insertQuizResult(
            QuizHistoryEntity(
                chapterName = chapterName,
                score = score,
                totalQuestions = total
            )
        )
    }
}
