package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MathCurriculumData
import com.example.data.local.MathEaseDatabase
import com.example.data.local.QuizHistoryEntity
import com.example.data.local.SavedFormulaEntity
import com.example.data.local.TopicConfidenceEntity
import com.example.data.repository.MathEaseRepository
import com.example.domain.SolverStepResult
import com.example.domain.SolversEngine
import com.example.model.Chapter
import com.example.model.Formula
import com.example.model.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    data object Home : Screen()
    data class ChapterDetail(val chapterId: String) : Screen()
    data object Solvers : Screen()
    data object Formulas : Screen()
    data object Practice : Screen()
    data object Tracker : Screen()
}

class MathEaseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MathEaseRepository

    init {
        val db = MathEaseDatabase.getDatabase(application)
        repository = MathEaseRepository(db.mathEaseDao())
    }

    // Navigation State
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val navigationBackStack = mutableListOf<Screen>(Screen.Home)

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            navigationBackStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (navigationBackStack.size > 1) {
            navigationBackStack.removeAt(navigationBackStack.size - 1)
            _currentScreen.value = navigationBackStack.last()
            return true
        }
        return false
    }

    // Chapters Data
    val chapters: List<Chapter> = MathCurriculumData.chapters
    val dailyTip: String = MathCurriculumData.dailyTips.random()

    // Confidences & Notes
    val topicConfidences: StateFlow<Map<String, TopicConfidenceEntity>> =
        repository.allTopicConfidences.map { list ->
            list.associateBy { it.topicId }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )

    val savedFormulas: StateFlow<List<SavedFormulaEntity>> =
        repository.allSavedFormulas.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val quizHistory: StateFlow<List<QuizHistoryEntity>> =
        repository.quizHistory.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun updateConfidence(topicId: String, chapterId: String, topicTitle: String, confidence: String) {
        viewModelScope.launch {
            repository.setTopicConfidence(topicId, chapterId, topicTitle, confidence)
        }
    }

    fun saveNote(topicId: String, chapterId: String, topicTitle: String, note: String) {
        viewModelScope.launch {
            repository.saveTopicNote(topicId, chapterId, topicTitle, note)
        }
    }

    fun toggleSaveFormula(formula: Formula) {
        viewModelScope.launch {
            val isSaved = savedFormulas.value.any { it.formulaId == formula.id }
            repository.toggleSaveFormula(formula, isSaved)
        }
    }

    // Formulas Search & Filter
    private val _formulaSearchQuery = MutableStateFlow("")
    val formulaSearchQuery: StateFlow<String> = _formulaSearchQuery.asStateFlow()

    private val _selectedFormulaCategory = MutableStateFlow("All")
    val selectedFormulaCategory: StateFlow<String> = _selectedFormulaCategory.asStateFlow()

    fun setFormulaSearchQuery(query: String) {
        _formulaSearchQuery.value = query
    }

    fun setFormulaCategory(cat: String) {
        _selectedFormulaCategory.value = cat
    }

    // Solvers State
    private val _quadraticResult = MutableStateFlow<SolverStepResult?>(null)
    val quadraticResult: StateFlow<SolverStepResult?> = _quadraticResult.asStateFlow()

    fun solveQuadratic(a: Double, b: Double, c: Double) {
        _quadraticResult.value = SolversEngine.solveQuadratic(a, b, c)
    }

    private val _trigResult = MutableStateFlow<SolverStepResult?>(null)
    val trigResult: StateFlow<SolverStepResult?> = _trigResult.asStateFlow()

    fun solveTrig(angle: Double) {
        _trigResult.value = SolversEngine.analyzeTrigAngle(angle)
    }

    private val _apGpResult = MutableStateFlow<SolverStepResult?>(null)
    val apGpResult: StateFlow<SolverStepResult?> = _apGpResult.asStateFlow()

    fun solveApGp(isAp: Boolean, a: Double, dOrR: Double, n: Int) {
        _apGpResult.value = SolversEngine.solveApGp(isAp, a, dOrR, n)
    }

    private val _pandCResult = MutableStateFlow<SolverStepResult?>(null)
    val pandCResult: StateFlow<SolverStepResult?> = _pandCResult.asStateFlow()

    fun solvePandC(n: Int, r: Int, orderMatters: Boolean) {
        _pandCResult.value = SolversEngine.solvePandC(n, r, orderMatters)
    }

    // Quiz State
    val quizQuestions: List<QuizQuestion> = MathCurriculumData.sampleQuizzes
    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex.asStateFlow()

    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()

    private val _showHint = MutableStateFlow(false)
    val showHint: StateFlow<Boolean> = _showHint.asStateFlow()

    private val _showExplanation = MutableStateFlow(false)
    val showExplanation: StateFlow<Boolean> = _showExplanation.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    fun selectQuizAnswer(index: Int) {
        if (_selectedAnswerIndex.value == null) {
            _selectedAnswerIndex.value = index
            _showExplanation.value = true
            val currentQ = quizQuestions.getOrNull(_currentQuizIndex.value)
            if (currentQ != null && index == currentQ.correctIndex) {
                _quizScore.value += 1
            }
        }
    }

    fun toggleHint() {
        _showHint.value = !_showHint.value
    }

    fun nextQuizQuestion() {
        if (_currentQuizIndex.value < quizQuestions.size - 1) {
            _currentQuizIndex.value += 1
            _selectedAnswerIndex.value = null
            _showHint.value = false
            _showExplanation.value = false
        } else {
            _isQuizFinished.value = true
            // Save to database
            viewModelScope.launch {
                repository.recordQuizScore(
                    chapterName = "Class 11 Mastery Quiz",
                    score = _quizScore.value,
                    total = quizQuestions.size
                )
            }
        }
    }

    fun restartQuiz() {
        _currentQuizIndex.value = 0
        _selectedAnswerIndex.value = null
        _showHint.value = false
        _showExplanation.value = false
        _quizScore.value = 0
        _isQuizFinished.value = false
    }
}
