package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.QuizHistoryEntity
import com.example.model.QuizQuestion
import com.example.ui.components.StepByStepBox
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PracticeScreen(
    questions: List<QuizQuestion>,
    currentIndex: Int,
    selectedAnswerIndex: Int?,
    showHint: Boolean,
    showExplanation: Boolean,
    quizScore: Int,
    isQuizFinished: Boolean,
    quizHistory: List<QuizHistoryEntity>,
    onSelectAnswer: (Int) -> Unit,
    onToggleHint: () -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recentHistory = androidx.compose.runtime.remember(quizHistory) { quizHistory.take(5) }
    val dateFormat = androidx.compose.runtime.remember {
        SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Class 11 Concept Practice",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Low-stress practice questions with instant hints and detailed step-by-step breakdowns.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isQuizFinished) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quiz_result_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Trophy",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Great Effort!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "You scored $quizScore out of ${questions.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (quizScore >= questions.size * 0.7) {
                                "Awesome work! You're really getting the hang of these fundamental concepts."
                            } else {
                                "Every mistake is a step toward understanding. Check the explanations and try again!"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = onRestartQuiz,
                            modifier = Modifier.testTag("restart_quiz_button")
                        ) {
                            Icon(imageVector = Icons.Default.Replay, contentDescription = "Retry")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Practice Again")
                        }
                    }
                }
            }
        } else {
            val currentQ = questions.getOrNull(currentIndex)
            if (currentQ != null) {
                item {
                    // Progress
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Question ${currentIndex + 1} of ${questions.size}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = currentQ.chapterName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { (currentIndex + 1).toFloat() / questions.size.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                    )
                }

                // Question Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("question_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = currentQ.question,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 24.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            // Options
                            currentQ.options.forEachIndexed { optIndex, optionText ->
                                val isSelected = selectedAnswerIndex == optIndex
                                val isCorrect = optIndex == currentQ.correctIndex
                                val isAnswered = selectedAnswerIndex != null

                                val (bgColor, borderColor, textColor) = when {
                                    !isAnswered -> Triple(
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.onSurface
                                    )
                                    isCorrect -> Triple(
                                        Color(0xFF10B981).copy(alpha = 0.15f),
                                        Color(0xFF10B981),
                                        Color(0xFF065F46)
                                    )
                                    isSelected -> Triple(
                                        Color(0xFFEF4444).copy(alpha = 0.15f),
                                        Color(0xFFEF4444),
                                        Color(0xFF991B1B)
                                    )
                                    else -> Triple(
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(bgColor)
                                        .border(
                                            width = if (isAnswered && (isCorrect || isSelected)) 2.dp else 1.dp,
                                            color = borderColor,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .clickable(enabled = !isAnswered) { onSelectAnswer(optIndex) }
                                        .padding(horizontal = 14.dp, vertical = 12.dp)
                                        .testTag("quiz_option_$optIndex")
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${('A' + optIndex)}. ",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = textColor
                                        )
                                        Text(
                                            text = optionText,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = if (isSelected || (isAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal,
                                            color = textColor,
                                            modifier = Modifier.weight(1f)
                                        )
                                        if (isAnswered) {
                                            if (isCorrect) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Correct",
                                                    tint = Color(0xFF10B981),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            } else if (isSelected) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Incorrect",
                                                    tint = Color(0xFFEF4444),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Hint button
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedButton(
                                    onClick = onToggleHint,
                                    modifier = Modifier.testTag("hint_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = "Hint",
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(if (showHint) "Hide Hint" else "Need a Hint?")
                                }

                                if (selectedAnswerIndex != null) {
                                    Button(
                                        onClick = onNextQuestion,
                                        modifier = Modifier.testTag("next_question_button")
                                    ) {
                                        Text(if (currentIndex < questions.size - 1) "Next Question" else "See Results")
                                    }
                                }
                            }

                            AnimatedVisibility(visible = showHint) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                                    )
                                ) {
                                    Text(
                                        text = "💡 Hint: ${currentQ.hint}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Step-by-step explanation
                if (showExplanation) {
                    item {
                        StepByStepBox(
                            title = "Step-by-Step Solution",
                            steps = currentQ.explanationSteps,
                            finalAnswer = currentQ.options[currentQ.correctIndex]
                        )
                    }
                }
            }
        }

        // Past quiz scores history
        if (quizHistory.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "History",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your Quiz Score History",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(recentHistory, key = { it.id }) { record ->
                val dateStr = dateFormat.format(Date(record.timestamp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = record.chapterName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = dateStr,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${record.score} / ${record.totalQuestions}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
