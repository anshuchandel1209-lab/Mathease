package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.domain.SolverStepResult
import com.example.ui.components.StepByStepBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolversScreen(
    quadraticResult: SolverStepResult?,
    trigResult: SolverStepResult?,
    apGpResult: SolverStepResult?,
    pandCResult: SolverStepResult?,
    onSolveQuadratic: (Double, Double, Double) -> Unit,
    onSolveTrig: (Double) -> Unit,
    onSolveApGp: (Boolean, Double, Double, Int) -> Unit,
    onSolvePandC: (Int, Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Quadratic & i", "Trig Angle", "AP & GP", "P & C Helper")

    Column(modifier = modifier.fillMaxSize()) {
        PrimaryTabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> QuadraticSolverView(result = quadraticResult, onSolve = onSolveQuadratic)
            1 -> TrigSolverView(result = trigResult, onSolve = onSolveTrig)
            2 -> ApGpSolverView(result = apGpResult, onSolve = onSolveApGp)
            3 -> PandCSolverView(result = pandCResult, onSolve = onSolvePandC)
        }
    }
}

@Composable
private fun QuadraticSolverView(
    result: SolverStepResult?,
    onSolve: (Double, Double, Double) -> Unit
) {
    var aStr by remember { mutableStateOf("1") }
    var bStr by remember { mutableStateOf("1") }
    var cStr by remember { mutableStateOf("1") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Quadratic & Complex Roots Solver",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Solves ax² + bx + c = 0 with full derivation. Handles D < 0 with complex numbers (i)!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            // Presets
            Text(text = "Quick Presets:", style = MaterialTheme.typography.labelSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = aStr == "1" && bStr == "1" && cStr == "1",
                    onClick = {
                        aStr = "1"; bStr = "1"; cStr = "1"
                        onSolve(1.0, 1.0, 1.0)
                    },
                    label = { Text("x² + x + 1 (D<0)") }
                )
                FilterChip(
                    selected = aStr == "1" && bStr == "-5" && cStr == "6",
                    onClick = {
                        aStr = "1"; bStr = "-5"; cStr = "6"
                        onSolve(1.0, -5.0, 6.0)
                    },
                    label = { Text("x² - 5x + 6 (D>0)") }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = aStr,
                    onValueChange = { aStr = it },
                    label = { Text("a") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("quad_input_a")
                )
                OutlinedTextField(
                    value = bStr,
                    onValueChange = { bStr = it },
                    label = { Text("b") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("quad_input_b")
                )
                OutlinedTextField(
                    value = cStr,
                    onValueChange = { cStr = it },
                    label = { Text("c") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("quad_input_c")
                )
            }
        }

        item {
            Button(
                onClick = {
                    val a = aStr.toDoubleOrNull() ?: 1.0
                    val b = bStr.toDoubleOrNull() ?: 0.0
                    val c = cStr.toDoubleOrNull() ?: 0.0
                    onSolve(a, b, c)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quad_solve_button")
            ) {
                Text("Break It Down Step-by-Step")
            }
        }

        if (result != null) {
            item {
                StepByStepBox(
                    title = result.title,
                    steps = result.steps,
                    finalAnswer = result.finalAnswer
                )
            }
        }
    }
}

@Composable
private fun TrigSolverView(
    result: SolverStepResult?,
    onSolve: (Double) -> Unit
) {
    var angleStr by remember { mutableStateOf("150") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Trigonometric Angle & Quadrant Explorer",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Enter any positive or negative angle. Unpacks Quadrant, ASTC sign rule, and function values.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Text(text = "Try Sample Angles:", style = MaterialTheme.typography.labelSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("120", "225", "300", "-45").forEach { ang ->
                    FilterChip(
                        selected = angleStr == ang,
                        onClick = {
                            angleStr = ang
                            onSolve(ang.toDouble())
                        },
                        label = { Text("$ang°") }
                    )
                }
            }
        }

        item {
            OutlinedTextField(
                value = angleStr,
                onValueChange = { angleStr = it },
                label = { Text("Angle in Degrees (e.g. 150)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("trig_angle_input")
            )
        }

        item {
            Button(
                onClick = {
                    val ang = angleStr.toDoubleOrNull() ?: 0.0
                    onSolve(ang)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("trig_solve_button")
            ) {
                Text("Analyze Quadrant & ASTC Signs")
            }
        }

        if (result != null) {
            item {
                StepByStepBox(
                    title = result.title,
                    steps = result.steps,
                    finalAnswer = result.finalAnswer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ApGpSolverView(
    result: SolverStepResult?,
    onSolve: (Boolean, Double, Double, Int) -> Unit
) {
    var isAp by remember { mutableStateOf(true) }
    var aStr by remember { mutableStateOf("3") }
    var dOrRStr by remember { mutableStateOf("4") }
    var nStr by remember { mutableStateOf("10") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "AP & GP Step-by-Step Solver",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Calculates nth term, sum of n terms, and infinite GP sum if applicable.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = isAp,
                    onClick = { isAp = true },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                ) {
                    Text("Arithmetic Progression (AP)")
                }
                SegmentedButton(
                    selected = !isAp,
                    onClick = { isAp = false },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                ) {
                    Text("Geometric Progression (GP)")
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = aStr,
                    onValueChange = { aStr = it },
                    label = { Text("1st Term (a)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = dOrRStr,
                    onValueChange = { dOrRStr = it },
                    label = { Text(if (isAp) "Diff (d)" else "Ratio (r)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = nStr,
                    onValueChange = { nStr = it },
                    label = { Text("Terms (n)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Button(
                onClick = {
                    val a = aStr.toDoubleOrNull() ?: 1.0
                    val dOrR = dOrRStr.toDoubleOrNull() ?: 1.0
                    val n = nStr.toIntOrNull() ?: 5
                    onSolve(isAp, a, dOrR, n)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("apgp_solve_button")
            ) {
                Text("Calculate Step-by-Step")
            }
        }

        if (result != null) {
            item {
                StepByStepBox(
                    title = result.title,
                    steps = result.steps,
                    finalAnswer = result.finalAnswer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PandCSolverView(
    result: SolverStepResult?,
    onSolve: (Int, Int, Boolean) -> Unit
) {
    var orderMatters by remember { mutableStateOf(false) }
    var nStr by remember { mutableStateOf("10") }
    var rStr by remember { mutableStateOf("3") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Permutation vs Combination Helper",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Unsure whether to use ⁿPᵣ or ⁿCᵣ? Answer the golden question below!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Text(
                text = "Golden Question: Does the order of arrangement matter?",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = !orderMatters,
                    onClick = { orderMatters = false },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                ) {
                    Text("No -> Selection (ⁿCᵣ)")
                }
                SegmentedButton(
                    selected = orderMatters,
                    onClick = { orderMatters = true },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                ) {
                    Text("Yes -> Order (ⁿPᵣ)")
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nStr,
                    onValueChange = { nStr = it },
                    label = { Text("Total items (n)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = rStr,
                    onValueChange = { rStr = it },
                    label = { Text("Chosen items (r)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Button(
                onClick = {
                    val n = nStr.toIntOrNull() ?: 1
                    val r = rStr.toIntOrNull() ?: 1
                    onSolve(n, r, orderMatters)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("pandc_solve_button")
            ) {
                Text("Expand Factorials & Compute")
            }
        }

        if (result != null) {
            item {
                StepByStepBox(
                    title = result.title,
                    steps = result.steps,
                    finalAnswer = result.finalAnswer
                )
            }
        }
    }
}
