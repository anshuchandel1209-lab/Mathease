package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.SavedFormulaEntity
import com.example.model.Chapter
import com.example.model.Formula
import com.example.ui.components.FormulaCard

@Composable
fun FormulasScreen(
    chapters: List<Chapter>,
    savedFormulas: List<SavedFormulaEntity>,
    searchQuery: String,
    selectedCategory: String,
    onSearchChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onToggleSaveFormula: (Formula) -> Unit,
    modifier: Modifier = Modifier
) {
    val allFormulas = remember(chapters) { chapters.flatMap { it.formulas } }
    val savedFormulaIds = remember(savedFormulas) { savedFormulas.map { it.formulaId }.toSet() }

    val categories = remember {
        listOf("All", "Bookmarked ⭐", "Trig", "Algebra", "Calculus", "Geometry", "Sequences")
    }

    val filteredFormulas = remember(allFormulas, searchQuery, selectedCategory, savedFormulaIds) {
        allFormulas.filter { formula ->
            val matchesSearch = searchQuery.isBlank() ||
                    formula.name.contains(searchQuery, ignoreCase = true) ||
                    formula.formula.contains(searchQuery, ignoreCase = true) ||
                    formula.chapterTitle.contains(searchQuery, ignoreCase = true)

            val matchesCategory = when (selectedCategory) {
                "All" -> true
                "Bookmarked ⭐" -> savedFormulaIds.contains(formula.id)
                else -> formula.category.equals(selectedCategory, ignoreCase = true)
            }

            matchesSearch && matchesCategory
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Formula Cheat Sheets",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Complete high-yield Class 11 formulas with notes on when and how to use them.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("formula_search_input"),
            placeholder = { Text("Search by name, symbol, e.g. 'sin 2x', 'limit'...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { onCategoryChange(cat) },
                    label = { Text(cat) },
                    modifier = Modifier.testTag("filter_$cat")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        if (filteredFormulas.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No formulas found",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (selectedCategory == "Bookmarked ⭐") "Tap the bookmark icon on any formula to save it here for fast revision." else "Try adjusting your search query or category filter.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredFormulas, key = { it.id }) { formula ->
                    val isSaved = savedFormulaIds.contains(formula.id)
                    FormulaCard(
                        formula = formula,
                        isSaved = isSaved,
                        onToggleSave = { onToggleSaveFormula(formula) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
