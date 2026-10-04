package com.example.model

enum class ConfidenceLevel(val displayName: String, val score: Int) {
    NEED_HELP("Needs Work", 0),
    PRACTICING("Practicing", 50),
    MASTERED("Mastered", 100);

    companion object {
        fun fromString(value: String): ConfidenceLevel {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: NEED_HELP
        }
    }
}

data class Chapter(
    val id: String,
    val number: Int,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val summary: String,
    val whyItMatters: String,
    val topics: List<Topic>,
    val formulas: List<Formula>,
    val pitfalls: List<Pitfall>,
    val memoryTricks: List<String>
)

data class Topic(
    val id: String,
    val chapterId: String,
    val title: String,
    val description: String,
    val keyPoints: List<String>,
    val exampleProblem: ExampleProblem? = null
)

data class ExampleProblem(
    val question: String,
    val hints: List<String>,
    val steps: List<String>,
    val answer: String
)

data class Formula(
    val id: String,
    val chapterId: String,
    val chapterTitle: String,
    val name: String,
    val formula: String,
    val whenToUse: String,
    val variables: String,
    val category: String
)

data class Pitfall(
    val mistake: String,
    val whyItHappens: String,
    val correctWay: String
)

data class QuizQuestion(
    val id: String,
    val chapterId: String,
    val chapterName: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val hint: String,
    val explanationSteps: List<String>
)
