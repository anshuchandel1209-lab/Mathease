package com.example.domain

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

data class SolverStepResult(
    val title: String,
    val summary: String,
    val steps: List<String>,
    val finalAnswer: String
)

object SolversEngine {

    fun solveQuadratic(a: Double, b: Double, c: Double): SolverStepResult {
        if (a == 0.0) {
            return SolverStepResult(
                title = "Linear Equation (a = 0)",
                summary = "Since a = 0, this is not a quadratic equation, but a linear one: bx + c = 0.",
                steps = listOf(
                    "Equation: ${b}x + ${c} = 0",
                    "Subtract $c from both sides: ${b}x = ${-c}",
                    "Divide by $b: x = ${-c / b}"
                ),
                finalAnswer = "x = ${String.format("%.3f", -c / b)}"
            )
        }

        val d = b * b - 4 * a * c
        val steps = mutableListOf<String>()
        steps.add("Step 1: Identify coefficients:")
        steps.add("   a = $a,  b = $b,  c = $c")
        steps.add("Step 2: Calculate Discriminant D = b² - 4ac:")
        steps.add("   D = ($b)² - 4($a)($c)")
        steps.add("   D = ${b * b} - ${4 * a * c} = $d")

        val finalAns: String

        if (d > 0) {
            val sqrtD = sqrt(d)
            steps.add("Step 3: Since D = $d > 0, the equation has TWO DISTINCT REAL roots.")
            steps.add("Step 4: Use quadratic formula x = (-b ± √D) / (2a):")
            steps.add("   x = (-($b) ± √$d) / (2 × $a)")
            steps.add("   x = (${-b} ± ${String.format("%.3f", sqrtD)}) / ${2 * a}")
            val r1 = (-b + sqrtD) / (2 * a)
            val r2 = (-b - sqrtD) / (2 * a)
            steps.add("   Root 1: x₁ = (${-b} + ${String.format("%.3f", sqrtD)}) / ${2 * a} = ${String.format("%.3f", r1)}")
            steps.add("   Root 2: x₂ = (${-b} - ${String.format("%.3f", sqrtD)}) / ${2 * a} = ${String.format("%.3f", r2)}")
            finalAns = "x₁ = ${String.format("%.3f", r1)},  x₂ = ${String.format("%.3f", r2)}"
        } else if (d == 0.0) {
            steps.add("Step 3: Since D = 0, the equation has ONE REPEATED REAL root.")
            steps.add("Step 4: x = -b / (2a):")
            val r = -b / (2 * a)
            steps.add("   x = -($b) / (2 × $a) = ${String.format("%.3f", r)}")
            finalAns = "x = ${String.format("%.3f", r)} (repeated root)"
        } else {
            val absD = abs(d)
            val sqrtAbsD = sqrt(absD)
            steps.add("Step 3: Notice D = $d < 0 (Negative Discriminant!).")
            steps.add("   In Class 10 this had 'No real roots'. In Class 11, we introduce i = √(-1).")
            steps.add("   √D = √(-$absD) = i√$absD ≈ ${String.format("%.3f", sqrtAbsD)} i")
            steps.add("Step 4: Apply formula x = (-b ± i√|D|) / (2a):")
            val realPart = -b / (2 * a)
            val imagPart = sqrtAbsD / (2 * abs(a))
            steps.add("   x = (-($b) ± ${String.format("%.3f", sqrtAbsD)} i) / ${2 * a}")
            steps.add("   Real part = ${-b} / ${2 * a} = ${String.format("%.3f", realPart)}")
            steps.add("   Imaginary part = ± ${String.format("%.3f", imagPart)} i")
            finalAns = "x = ${String.format("%.3f", realPart)} ± ${String.format("%.3f", imagPart)} i"
        }

        return SolverStepResult(
            title = "Quadratic & Complex Roots",
            summary = "Solved ax² + bx + c = 0 for a=$a, b=$b, c=$c",
            steps = steps,
            finalAnswer = finalAns
        )
    }

    fun analyzeTrigAngle(angleDeg: Double): SolverStepResult {
        // Normalize angle to [0, 360)
        var norm = angleDeg % 360.0
        if (norm < 0) norm += 360.0

        val rad = Math.toRadians(norm)
        val steps = mutableListOf<String>()
        steps.add("Step 1: Input angle = $angleDeg°")
        if (angleDeg != norm) {
            steps.add("   Normalized to standard circle [0°, 360°): $norm°")
        }

        val quadrant: String
        val astcRule: String
        val refAngle: Double

        when {
            norm == 0.0 || norm == 360.0 -> {
                quadrant = "Positive X-axis (Boundary)"
                astcRule = "sin=0, cos=1, tan=0"
                refAngle = 0.0
            }
            norm > 0 && norm < 90 -> {
                quadrant = "Quadrant I (0° to 90°)"
                astcRule = "ASTC: 'ALL' -> All trigonometric functions are POSITIVE (+)."
                refAngle = norm
            }
            norm == 90.0 -> {
                quadrant = "Positive Y-axis (Boundary)"
                astcRule = "sin=1, cos=0, tan=undefined"
                refAngle = 90.0
            }
            norm > 90 && norm < 180 -> {
                quadrant = "Quadrant II (90° to 180°)"
                astcRule = "ASTC: 'SILVER' -> Only SIN and CSC are POSITIVE (+), others are NEGATIVE (-)."
                refAngle = 180.0 - norm
            }
            norm == 180.0 -> {
                quadrant = "Negative X-axis (Boundary)"
                astcRule = "sin=0, cos=-1, tan=0"
                refAngle = 0.0
            }
            norm > 180 && norm < 270 -> {
                quadrant = "Quadrant III (180° to 270°)"
                astcRule = "ASTC: 'TEA' -> Only TAN and COT are POSITIVE (+), Sin and Cos are NEGATIVE (-)."
                refAngle = norm - 180.0
            }
            norm == 270.0 -> {
                quadrant = "Negative Y-axis (Boundary)"
                astcRule = "sin=-1, cos=0, tan=undefined"
                refAngle = 90.0
            }
            else -> {
                quadrant = "Quadrant IV (270° to 360°)"
                astcRule = "ASTC: 'CUPS' -> Only COS and SEC are POSITIVE (+), Sin and Tan are NEGATIVE (-)."
                refAngle = 360.0 - norm
            }
        }

        steps.add("Step 2: Location: $quadrant")
        steps.add("Step 3: ASTC Sign Rule:\n   $astcRule")
        steps.add("Step 4: Reference Angle α = ${String.format("%.1f", refAngle)}°")

        val s = sin(rad)
        val c = cos(rad)
        val t = if (abs(c) < 1e-9) Double.NaN else tan(rad)

        steps.add("Step 5: Function Values:")
        steps.add("   sin($norm°) = ${String.format("%.4f", s)}")
        steps.add("   cos($norm°) = ${String.format("%.4f", c)}")
        steps.add("   tan($norm°) = ${if (t.isNaN()) "Undefined (div by 0)" else String.format("%.4f", t)}")

        val ans = "Quadrant: $quadrant | sin=${String.format("%.2f", s)}, cos=${String.format("%.2f", c)}, tan=${if (t.isNaN()) "Undef" else String.format("%.2f", t)}"

        return SolverStepResult(
            title = "Angle Analysis ($angleDeg°)",
            summary = "$quadrant with Reference Angle ${String.format("%.1f", refAngle)}°",
            steps = steps,
            finalAnswer = ans
        )
    }

    fun solveApGp(
        isAp: Boolean,
        a: Double,
        diffOrRatio: Double,
        n: Int
    ): SolverStepResult {
        val steps = mutableListOf<String>()

        return if (isAp) {
            val d = diffOrRatio
            steps.add("Arithmetic Progression (AP):")
            steps.add("   First term a = $a")
            steps.add("   Common difference d = $d")
            steps.add("   Number of terms n = $n")
            steps.add("Step 1: Calculate nth term using formula aₙ = a + (n - 1)d:")
            val an = a + (n - 1) * d
            steps.add("   a_$n = $a + ($n - 1) × ($d)")
            steps.add("   a_$n = $a + ${n - 1} × ($d) = $an")

            steps.add("Step 2: Calculate sum of first n terms using Sₙ = (n/2)[2a + (n-1)d]:")
            val sn = (n.toDouble() / 2.0) * (2 * a + (n - 1) * d)
            steps.add("   S_$n = ($n / 2) × [2($a) + ($n - 1)($d)]")
            steps.add("   S_$n = ${n / 2.0} × [${2 * a} + ${(n - 1) * d}] = $sn")

            SolverStepResult(
                title = "AP Calculation",
                summary = "First $n terms of AP with a=$a, d=$d",
                steps = steps,
                finalAnswer = "a_$n = $an,  S_$n = $sn"
            )
        } else {
            val r = diffOrRatio
            steps.add("Geometric Progression (GP):")
            steps.add("   First term a = $a")
            steps.add("   Common ratio r = $r")
            steps.add("   Number of terms n = $n")

            steps.add("Step 1: Calculate nth term using formula aₙ = a × rⁿ⁻¹:")
            val an = a * Math.pow(r, (n - 1).toDouble())
            steps.add("   a_$n = $a × ($r)^${n - 1} = $an")

            steps.add("Step 2: Calculate Sum of first n terms:")
            val sn: Double
            if (r == 1.0) {
                sn = a * n
                steps.add("   Since r = 1, S_$n = n × a = $n × $a = $sn")
            } else {
                sn = a * (Math.pow(r, n.toDouble()) - 1.0) / (r - 1.0)
                steps.add("   S_$n = a(rⁿ - 1) / (r - 1)")
                steps.add("   S_$n = $a × (($r)^$n - 1) / ($r - 1) = $sn")
            }

            if (abs(r) < 1.0) {
                steps.add("Step 3: Infinite Sum S_∞ (since |r| = ${abs(r)} < 1):")
                val sInf = a / (1.0 - r)
                steps.add("   S_∞ = a / (1 - r) = $a / (1 - $r) = ${String.format("%.4f", sInf)}")
            } else {
                steps.add("Step 3: Infinite Sum does NOT converge because |r| = ${abs(r)} ≥ 1.")
            }

            SolverStepResult(
                title = "GP Calculation",
                summary = "First $n terms of GP with a=$a, r=$r",
                steps = steps,
                finalAnswer = "a_$n = ${String.format("%.3f", an)},  S_$n = ${String.format("%.3f", sn)}"
            )
        }
    }

    fun solvePandC(n: Int, r: Int, orderMatters: Boolean): SolverStepResult {
        if (n < 0 || r < 0 || r > n) {
            return SolverStepResult(
                title = "Invalid Input",
                summary = "Requires n ≥ r ≥ 0.",
                steps = listOf("n must be greater than or equal to r, and both non-negative."),
                finalAnswer = "Invalid (n=$n, r=$r)"
            )
        }

        fun factorial(x: Int): Long {
            var res = 1L
            for (i in 2..x) res *= i
            return res
        }

        val steps = mutableListOf<String>()
        val nFact = factorial(n)
        val rFact = factorial(r)
        val nmrFact = factorial(n - r)

        if (orderMatters) {
            steps.add("Decision: ORDER MATTERS (Positions / Arrangements / Ranking)")
            steps.add("Use Permutations formula: ⁿPᵣ = n! / (n - r)!")
            steps.add("Step 1: Calculate factorials:")
            steps.add("   n! = $n! = $nFact")
            steps.add("   (n - r)! = ($n - $r)! = ${n - r}! = $nmrFact")
            val nPr = nFact / nmrFact
            steps.add("Step 2: ⁿPᵣ = $nFact / $nmrFact = $nPr")
            return SolverStepResult(
                title = "Permutation: ⁿPᵣ ($n items taken $r at a time)",
                summary = "Arrangement count where order counts",
                steps = steps,
                finalAnswer = "ⁿPᵣ = $nPr ways"
            )
        } else {
            steps.add("Decision: ORDER DOES NOT MATTER (Selections / Teams / Groups)")
            steps.add("Use Combinations formula: ⁿCᵣ = n! / [r! (n - r)!]")
            steps.add("Step 1: Calculate factorials:")
            steps.add("   n! = $nFact")
            steps.add("   r! = $r! = $rFact")
            steps.add("   (n - r)! = ${n - r}! = $nmrFact")
            val nCr = nFact / (rFact * nmrFact)
            steps.add("Step 2: ⁿCᵣ = $nFact / ($rFact × $nmrFact) = $nCr")
            steps.add("Tip: Notice ⁿCᵣ = ⁿCₙ₋ᵣ = $nCr (selecting $r is the same as leaving out ${n - r}!)")
            return SolverStepResult(
                title = "Combination: ⁿCᵣ ($n choose $r)",
                summary = "Selection count where order doesn't matter",
                steps = steps,
                finalAnswer = "ⁿCᵣ = $nCr combinations"
            )
        }
    }
}
