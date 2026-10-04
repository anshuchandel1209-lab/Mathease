package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.TopicConfidenceEntity
import com.example.model.Chapter
import com.example.model.Topic
import com.example.ui.components.ConfidenceSelector

@Composable
fun TrackerScreen(
    chapters: List<Chapter>,
    topicConfidences: Map<String, TopicConfidenceEntity>,
    onUpdateConfidence: (topicId: String, chapterId: String, topicTitle: String, confidence: String) -> Unit,
    onSaveNote: (topicId: String, chapterId: String, topicTitle: String, note: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val allTopicsWithChapter = remember(chapters) {
        chapters.flatMap { ch -> ch.topics.map { t -> Pair(t, ch) } }
    }

    var selectedFilter by remember { mutableStateOf("ALL") }

    val totalTopics = allTopicsWithChapter.size
    val (needHelpCount, practicingCount, masteredCount, score) = remember(allTopicsWithChapter, topicConfidences) {
        var need = 0
        var prac = 0
        var mast = 0
        for ((topic, _) in allTopicsWithChapter) {
            when (topicConfidences[topic.id]?.confidence) {
                "MASTERED" -> mast++
                "PRACTICING" -> prac++
                else -> need++
            }
        }
        val sc = if (totalTopics > 0) ((prac * 0.5 + mast * 1.0) / totalTopics * 100).toInt() else 0
        listOf(need, prac, mast, sc)
    }

    val filteredList = remember(allTopicsWithChapter, topicConfidences, selectedFilter) {
        allTopicsWithChapter.filter { (topic, _) ->
            val conf = topicConfidences[topic.id]?.confidence ?: "NEED_HELP"
            when (selectedFilter) {
                "NEED_HELP" -> conf == "NEED_HELP"
                "PRACTICING" -> conf == "PRACTICING"
                "MASTERED" -> conf == "MASTERED"
                else -> true
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "My Weak Spots & Confidence",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Target topics you find difficult, track your progress, and build your maths confidence.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Progress Overview Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("confidence_overview_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Overall Syllabus Confidence",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "$score%",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { score / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TrackerStatBadge("🔴 Needs Work", needHelpCount, Color(0xFFEF4444))
                        TrackerStatBadge("🟡 Practicing", practicingCount, Color(0xFFF59E0B))
                        TrackerStatBadge("🟢 Mastered", masteredCount, Color(0xFF10B981))
                    }
                }
            }
        }

        // Friendly advice card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "Mindset",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Math Anxiety Truth:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Nobody is born 'good' or 'bad' at maths. Class 11 is just a different dialect. Once you practice the fundamental 3-4 steps for each pattern, muscle memory takes over.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Filter chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == "ALL",
                    onClick = { selectedFilter = "ALL" },
                    label = { Text("All ($totalTopics)") }
                )
                FilterChip(
                    selected = selectedFilter == "NEED_HELP",
                    onClick = { selectedFilter = "NEED_HELP" },
                    label = { Text("Needs Work ($needHelpCount)") }
                )
                FilterChip(
                    selected = selectedFilter == "PRACTICING",
                    onClick = { selectedFilter = "PRACTICING" },
                    label = { Text("Practicing ($practicingCount)") }
                )
                FilterChip(
                    selected = selectedFilter == "MASTERED",
                    onClick = { selectedFilter = "MASTERED" },
                    label = { Text("Mastered ($masteredCount)") }
                )
            }
        }

        // Filtered topics list
        items(filteredList, key = { it.first.id }) { (topic, chapter) ->
            val entity = topicConfidences[topic.id]
            val currentConf = entity?.confidence ?: "NEED_HELP"
            val savedNote = entity?.note ?: ""

            TrackerTopicItem(
                topic = topic,
                chapter = chapter,
                currentConfidence = currentConf,
                initialNote = savedNote,
                onUpdateConfidence = { conf ->
                    onUpdateConfidence(topic.id, chapter.id, topic.title, conf)
                },
                onSaveNote = { note ->
                    onSaveNote(topic.id, chapter.id, topic.title, note)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TrackerStatBadge(label: String, count: Int, color: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}

@Composable
private fun TrackerTopicItem(
    topic: Topic,
    chapter: Chapter,
    currentConfidence: String,
    initialNote: String,
    onUpdateConfidence: (String) -> Unit,
    onSaveNote: (String) -> Unit
) {
    var showNoteEditor by remember { mutableStateOf(false) }
    var noteText by remember(initialNote) { mutableStateOf(initialNote) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "Ch ${chapter.number}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = topic.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))
            ConfidenceSelector(
                currentConfidence = currentConfidence,
                onSelect = onUpdateConfidence
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (initialNote.isNotBlank()) "📝 $initialNote" else "No notes",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (initialNote.isNotBlank()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )
                IconButton(
                    onClick = { showNoteEditor = !showNoteEditor },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Note",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            AnimatedVisibility(visible = showNoteEditor) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("What trips you up in this topic?") },
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = {
                            onSaveNote(noteText)
                            showNoteEditor = false
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Save Note")
                    }
                }
            }
        }
    }
}
