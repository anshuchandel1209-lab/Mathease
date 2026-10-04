package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.ChapterDetailScreen
import com.example.ui.screens.FormulasScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.SolversScreen
import com.example.ui.screens.TrackerScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MathEaseViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MathEaseApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathEaseApp(
    viewModel: MathEaseViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val topicConfidences by viewModel.topicConfidences.collectAsStateWithLifecycle()
    val savedFormulas by viewModel.savedFormulas.collectAsStateWithLifecycle()
    val quizHistory by viewModel.quizHistory.collectAsStateWithLifecycle()

    val formulaSearchQuery by viewModel.formulaSearchQuery.collectAsStateWithLifecycle()
    val selectedFormulaCategory by viewModel.selectedFormulaCategory.collectAsStateWithLifecycle()

    val quadraticResult by viewModel.quadraticResult.collectAsStateWithLifecycle()
    val trigResult by viewModel.trigResult.collectAsStateWithLifecycle()
    val apGpResult by viewModel.apGpResult.collectAsStateWithLifecycle()
    val pandCResult by viewModel.pandCResult.collectAsStateWithLifecycle()

    val currentQuizIndex by viewModel.currentQuizIndex.collectAsStateWithLifecycle()
    val selectedAnswerIndex by viewModel.selectedAnswerIndex.collectAsStateWithLifecycle()
    val showHint by viewModel.showHint.collectAsStateWithLifecycle()
    val showExplanation by viewModel.showExplanation.collectAsStateWithLifecycle()
    val quizScore by viewModel.quizScore.collectAsStateWithLifecycle()
    val isQuizFinished by viewModel.isQuizFinished.collectAsStateWithLifecycle()

    // Handle back press if not on Home screen
    if (currentScreen != Screen.Home) {
        BackHandler {
            viewModel.navigateBack()
        }
    }

    if (currentScreen is Screen.ChapterDetail) {
        val chapterId = (currentScreen as Screen.ChapterDetail).chapterId
        val chapter = viewModel.chapters.find { it.id == chapterId } ?: viewModel.chapters.first()
        ChapterDetailScreen(
            chapter = chapter,
            topicConfidences = topicConfidences,
            savedFormulas = savedFormulas,
            onBack = { viewModel.navigateBack() },
            onUpdateConfidence = { topicId, chId, title, conf ->
                viewModel.updateConfidence(topicId, chId, title, conf)
            },
            onSaveNote = { topicId, chId, title, note ->
                viewModel.saveNote(topicId, chId, title, note)
            },
            onToggleSaveFormula = { formula ->
                viewModel.toggleSaveFormula(formula)
            }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "MathEase 11",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.testTag("bottom_nav_bar"),
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = currentScreen is Screen.Home,
                        onClick = { viewModel.navigateTo(Screen.Home) },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = "Learn"
                            )
                        },
                        label = { Text("Learn") },
                        modifier = Modifier.testTag("nav_item_learn")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.Solvers,
                        onClick = { viewModel.navigateTo(Screen.Solvers) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = "Solvers"
                            )
                        },
                        label = { Text("Solvers") },
                        modifier = Modifier.testTag("nav_item_solvers")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.Formulas,
                        onClick = { viewModel.navigateTo(Screen.Formulas) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Functions,
                                contentDescription = "Formulas"
                            )
                        },
                        label = { Text("Formulas") },
                        modifier = Modifier.testTag("nav_item_formulas")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.Practice,
                        onClick = { viewModel.navigateTo(Screen.Practice) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = "Practice"
                            )
                        },
                        label = { Text("Practice") },
                        modifier = Modifier.testTag("nav_item_practice")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.Tracker,
                        onClick = { viewModel.navigateTo(Screen.Tracker) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.TrackChanges,
                                contentDescription = "Weak Spots"
                            )
                        },
                        label = { Text("Weak Spots") },
                        modifier = Modifier.testTag("nav_item_tracker")
                    )
                }
            }
        ) { innerPadding ->
            when (currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        chapters = viewModel.chapters,
                        dailyTip = viewModel.dailyTip,
                        topicConfidences = topicConfidences,
                        onSelectChapter = { id -> viewModel.navigateTo(Screen.ChapterDetail(id)) },
                        onNavigate = { screen -> viewModel.navigateTo(screen) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                is Screen.Solvers -> {
                    SolversScreen(
                        quadraticResult = quadraticResult,
                        trigResult = trigResult,
                        apGpResult = apGpResult,
                        pandCResult = pandCResult,
                        onSolveQuadratic = { a, b, c -> viewModel.solveQuadratic(a, b, c) },
                        onSolveTrig = { ang -> viewModel.solveTrig(ang) },
                        onSolveApGp = { isAp, a, dOrR, n -> viewModel.solveApGp(isAp, a, dOrR, n) },
                        onSolvePandC = { n, r, order -> viewModel.solvePandC(n, r, order) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                is Screen.Formulas -> {
                    FormulasScreen(
                        chapters = viewModel.chapters,
                        savedFormulas = savedFormulas,
                        searchQuery = formulaSearchQuery,
                        selectedCategory = selectedFormulaCategory,
                        onSearchChange = { q -> viewModel.setFormulaSearchQuery(q) },
                        onCategoryChange = { c -> viewModel.setFormulaCategory(c) },
                        onToggleSaveFormula = { f -> viewModel.toggleSaveFormula(f) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                is Screen.Practice -> {
                    PracticeScreen(
                        questions = viewModel.quizQuestions,
                        currentIndex = currentQuizIndex,
                        selectedAnswerIndex = selectedAnswerIndex,
                        showHint = showHint,
                        showExplanation = showExplanation,
                        quizScore = quizScore,
                        isQuizFinished = isQuizFinished,
                        quizHistory = quizHistory,
                        onSelectAnswer = { idx -> viewModel.selectQuizAnswer(idx) },
                        onToggleHint = { viewModel.toggleHint() },
                        onNextQuestion = { viewModel.nextQuizQuestion() },
                        onRestartQuiz = { viewModel.restartQuiz() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                is Screen.Tracker -> {
                    TrackerScreen(
                        chapters = viewModel.chapters,
                        topicConfidences = topicConfidences,
                        onUpdateConfidence = { topicId, chId, title, conf ->
                            viewModel.updateConfidence(topicId, chId, title, conf)
                        },
                        onSaveNote = { topicId, chId, title, note ->
                            viewModel.saveNote(topicId, chId, title, note)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                else -> Unit
            }
        }
    }
}
