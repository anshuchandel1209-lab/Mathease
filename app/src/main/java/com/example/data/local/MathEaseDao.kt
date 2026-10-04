package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MathEaseDao {
    @Query("SELECT * FROM topic_confidence")
    fun getAllTopicConfidences(): Flow<List<TopicConfidenceEntity>>

    @Query("SELECT * FROM topic_confidence WHERE topicId = :topicId")
    suspend fun getTopicConfidence(topicId: String): TopicConfidenceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTopicConfidence(entity: TopicConfidenceEntity)

    @Query("UPDATE topic_confidence SET note = :note, updatedAt = :timestamp WHERE topicId = :topicId")
    suspend fun updateTopicNote(topicId: String, note: String, timestamp: Long = System.currentTimeMillis())

    // Saved Formulas
    @Query("SELECT * FROM saved_formulas ORDER BY savedAt DESC")
    fun getAllSavedFormulas(): Flow<List<SavedFormulaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFormula(formula: SavedFormulaEntity)

    @Query("DELETE FROM saved_formulas WHERE formulaId = :formulaId")
    suspend fun removeSavedFormula(formulaId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_formulas WHERE formulaId = :formulaId)")
    fun isFormulaSaved(formulaId: String): Flow<Boolean>

    // Quiz History
    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    fun getQuizHistory(): Flow<List<QuizHistoryEntity>>

    @Insert
    suspend fun insertQuizResult(result: QuizHistoryEntity)
}
