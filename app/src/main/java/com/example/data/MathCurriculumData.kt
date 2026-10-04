package com.example.data

import com.example.model.Chapter
import com.example.model.ExampleProblem
import com.example.model.Formula
import com.example.model.Pitfall
import com.example.model.QuizQuestion
import com.example.model.Topic

object MathCurriculumData {

    val dailyTips = listOf(
        "🧠 Math Anxiety Tip: If a problem looks huge, don't try to solve it in one go. Just write down 'Given' and 'To Find'. Once you label what you know, the next step often reveals itself!",
        "💡 Quick Rule of Signs: When multiplying or dividing both sides of an inequality by a NEGATIVE number, always reverse the inequality symbol! (e.g. -2x < 6 becomes x > -3).",
        "🎯 The ASTC Rule: In Trigonometry, remember 'All Silver Tea Cups' (Quad I: All positive, Quad II: Sin positive, Quad III: Tan positive, Quad IV: Cos positive).",
        "⚡ P&C Golden Trick: If the order or arrangement matters (like seating, ranking, words), use Permutations (P). If only selection matters (like choosing team members), use Combinations (C).",
        "✨ Don't Fear 'i': The imaginary number i is just a symbol for √(-1). Just remember i² = -1. Any power of i repeats every 4 powers (i¹=i, i²=-1, i³=-i, i⁴=1)!",
        "📐 Conics Secret: For any conic section, eccentricity 'e' tells the story: e = 0 is a Circle, e = 1 is a Parabola, e < 1 is an Ellipse, e > 1 is a Hyperbola!",
        "🚀 Calculus Intuition: A limit lim(x→a) f(x) does NOT ask what happens AT x=a. It asks what f(x) gets closer to as x creeps infinitely close to a without touching it."
    )

    val chapters: List<Chapter> = listOf(
        Chapter(
            id = "sets_relations",
            number = 1,
            title = "Sets, Relations & Functions",
            subtitle = "The language of all modern mathematics",
            iconName = "shapes",
            summary = "A set is just a well-defined collection of objects. Relations connect elements of two sets, and Functions are special relations where every input has exactly one output.",
            whyItMatters = "Every chapter in Class 11 & 12 (Calculus, Trigonometry, Probability) uses Set notation and Function terminology.",
            topics = listOf(
                Topic(
                    id = "sets_subsets",
                    chapterId = "sets_relations",
                    title = "Subsets & Power Set",
                    description = "A set A is a subset of B (A ⊆ B) if every element of A is also in B. The power set P(A) is the set of all subsets.",
                    keyPoints = listOf(
                        "Empty set ∅ is a subset of every set.",
                        "Every set is a subset of itself: A ⊆ A.",
                        "If a set has n elements, total number of subsets is 2ⁿ.",
                        "Total number of proper subsets is 2ⁿ - 1."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "If set A = {1, 2, 3}, find the number of subsets and list the power set P(A).",
                        hints = listOf("Use formula 2ⁿ where n = 3", "Don't forget the empty set ∅"),
                        steps = listOf(
                            "Number of elements n = 3.",
                            "Total subsets = 2³ = 8.",
                            "Subsets with 0 elements: ∅",
                            "Subsets with 1 element: {1}, {2}, {3}",
                            "Subsets with 2 elements: {1,2}, {1,3}, {2,3}",
                            "Subsets with 3 elements: {1,2,3}"
                        ),
                        answer = "P(A) = {∅, {1}, {2}, {3}, {1,2}, {1,3}, {2,3}, {1,2,3}} (8 subsets)"
                    )
                ),
                Topic(
                    id = "set_operations",
                    chapterId = "sets_relations",
                    title = "Union, Intersection & De Morgan's Laws",
                    description = "Union (∪) means 'OR' (all items combined). Intersection (∩) means 'AND' (common items only).",
                    keyPoints = listOf(
                        "n(A ∪ B) = n(A) + n(B) - n(A ∩ B)",
                        "Disjoint sets have no common elements: A ∩ B = ∅",
                        "De Morgan's 1st Law: (A ∪ B)' = A' ∩ B'",
                        "De Morgan's 2nd Law: (A ∩ B)' = A' ∪ B'"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "In a class of 50 students, 30 play cricket, 25 play football, and 10 play both. How many play at least one sport?",
                        hints = listOf("Use n(A ∪ B) = n(A) + n(B) - n(A ∩ B)"),
                        steps = listOf(
                            "Let C = cricket, F = football.",
                            "n(C) = 30, n(F) = 25, n(C ∩ F) = 10.",
                            "n(C ∪ F) = 30 + 25 - 10 = 45."
                        ),
                        answer = "45 students play at least one sport."
                    )
                ),
                Topic(
                    id = "relations_functions",
                    chapterId = "sets_relations",
                    title = "Domain, Range & Functions",
                    description = "A function f: A → B maps each element x ∈ A to exactly ONE element y ∈ B. Domain is all allowed inputs, Range is all actual outputs.",
                    keyPoints = listOf(
                        "Vertical Line Test: If any vertical line cuts a graph more than once, it is NOT a function.",
                        "Modulus function: |x| = x (if x ≥ 0) and -x (if x < 0). Range is [0, ∞).",
                        "Greatest integer function [x]: rounds down to nearest integer (e.g. [2.7] = 2, [-1.3] = -2)."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the domain of f(x) = 1 / √(4 - x²).",
                        hints = listOf("The quantity inside a square root must be non-negative", "The denominator cannot be zero"),
                        steps = listOf(
                            "For real output, the expression under square root must be > 0 (strictly positive because it is in denominator).",
                            "4 - x² > 0",
                            "(2 - x)(2 + x) > 0  =>  x² < 4",
                            "-2 < x < 2"
                        ),
                        answer = "Domain = (-2, 2)"
                    )
                )
            ),
            formulas = listOf(
                Formula("f1", "sets_relations", "Sets", "Total Subsets Formula", "Total Subsets = 2ⁿ", "Finding how many subsets a set of size n has", "n = number of elements", "Algebra"),
                Formula("f2", "sets_relations", "Sets", "Union Cardinality", "n(A ∪ B) = n(A) + n(B) - n(A ∩ B)", "Finding total in combined sets", "n(A) = size of A", "Algebra"),
                Formula("f3", "sets_relations", "Sets", "De Morgan's Law 1", "(A ∪ B)' = A' ∩ B'", "Complement of union equals intersection of complements", "A' = complement of A", "Algebra"),
                Formula("f4", "sets_relations", "Sets", "De Morgan's Law 2", "(A ∩ B)' = A' ∪ B'", "Complement of intersection equals union of complements", "B' = complement of B", "Algebra")
            ),
            pitfalls = listOf(
                Pitfall(
                    mistake = "Confusing ∈ (belongs to) with ⊆ (is a subset of)",
                    whyItHappens = "Saying {1} ∈ {1, 2, 3} or 1 ⊆ {1, 2, 3}.",
                    correctWay = "An element belongs: 1 ∈ {1, 2, 3}. A set is a subset: {1} ⊆ {1, 2, 3}."
                ),
                Pitfall(
                    mistake = "Forgetting empty set ∅ in the power set",
                    whyItHappens = "Counting subsets and missing the set containing nothing.",
                    correctWay = "Always include ∅ as the very first subset of any set!"
                )
            ),
            memoryTricks = listOf(
                "Power set size is always a power of 2: 2ⁿ!",
                "Intersection is the 'n' in cap: ∩ looks like an n (commoN elements)."
            )
        ),

        Chapter(
            id = "trigonometry",
            number = 2,
            title = "Trigonometric Functions",
            subtitle = "Master quadrants, signs & formula patterns",
            iconName = "trig",
            summary = "Trigonometry extends from right triangles to unit circles. Angles can be negative and exceed 360°. Master the ASTC quadrant rule and the core formula symmetries.",
            whyItMatters = "Trigonometry appears in 60%+ of Class 12 Calculus problems. Once you learn the pattern, you don't need to memorize 40 separate formulas!",
            topics = listOf(
                Topic(
                    id = "trig_astc",
                    chapterId = "trigonometry",
                    title = "ASTC Rule & Quadrant Signs",
                    description = "The ASTC (All Silver Tea Cups) rule determines whether sin, cos, tan are positive or negative in each quadrant.",
                    keyPoints = listOf(
                        "Quadrant I (0° to 90°): ALL positive (sin, cos, tan, csc, sec, cot).",
                        "Quadrant II (90° to 180°): SILVER -> Only SIN and CSC are positive.",
                        "Quadrant III (180° to 270°): TEA -> Only TAN and COT are positive.",
                        "Quadrant IV (270° to 360°): CUPS -> Only COS and SEC are positive.",
                        "Even/Odd: cos(-θ) = cos(θ), but sin(-θ) = -sin(θ) and tan(-θ) = -tan(θ)."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the exact value of sin(150°).",
                        hints = listOf("150° is in Quadrant II", "Write 150° as 180° - 30°"),
                        steps = listOf(
                            "Identify quadrant: 150° lies in Quadrant II.",
                            "In Quad II, sine is POSITIVE (ASTC: 'Silver').",
                            "Use reference angle: sin(150°) = sin(180° - 30°).",
                            "sin(180° - θ) = +sin(θ) = sin(30°).",
                            "sin(30°) = 1/2."
                        ),
                        answer = "sin(150°) = 1/2"
                    )
                ),
                Topic(
                    id = "trig_compound",
                    chapterId = "trigonometry",
                    title = "Compound Angles: sin(A ± B) & cos(A ± B)",
                    description = "These are the mother formulas of all trigonometry. Almost every double angle and sum-to-product formula comes directly from them.",
                    keyPoints = listOf(
                        "sin(A + B) = sin A cos B + cos A sin B (signs match)",
                        "sin(A - B) = sin A cos B - cos A sin B",
                        "cos(A + B) = cos A cos B - sin A sin B (sign flips!)",
                        "cos(A - B) = cos A cos B + sin A sin B (sign flips!)"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the exact value of cos(75°).",
                        hints = listOf("Break 75° into known angles: 45° + 30°", "Use cos(A + B) formula"),
                        steps = listOf(
                            "Write cos(75°) = cos(45° + 30°).",
                            "Apply cos(A + B) = cos A cos B - sin A sin B.",
                            "= cos(45°) cos(30°) - sin(45°) sin(30°).",
                            "= (1/√2) × (√3/2) - (1/√2) × (1/2).",
                            "= (√3 - 1) / (2√2)."
                        ),
                        answer = "cos(75°) = (√3 - 1) / (2√2)"
                    )
                ),
                Topic(
                    id = "trig_double",
                    chapterId = "trigonometry",
                    title = "Double Angles: sin(2x) & cos(2x)",
                    description = "Double angle formulas allow you to halve powers or double angles. Vital for integrals in Class 12.",
                    keyPoints = listOf(
                        "sin(2x) = 2 sin x cos x",
                        "cos(2x) = cos²x - sin²x = 2cos²x - 1 = 1 - 2sin²x",
                        "Power reducer: 1 - cos(2x) = 2sin²x (SUPER IMPORTANT)",
                        "Power reducer: 1 + cos(2x) = 2cos²x (SUPER IMPORTANT)",
                        "tan(2x) = 2 tan x / (1 - tan²x)"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "If sin x = 3/5 and x is in Quadrant I, find sin(2x).",
                        hints = listOf("First find cos x using cos²x = 1 - sin²x", "Then apply sin(2x) = 2 sin x cos x"),
                        steps = listOf(
                            "In Quad I, cos x is positive.",
                            "cos x = √(1 - sin²x) = √(1 - (9/25)) = √(16/25) = 4/5.",
                            "sin(2x) = 2 sin x cos x = 2 × (3/5) × (4/5) = 24/25."
                        ),
                        answer = "sin(2x) = 24/25"
                    )
                )
            ),
            formulas = listOf(
                Formula("trig1", "trigonometry", "Trigonometry", "Radian to Degree", "1 rad = 180°/π ≈ 57.3°", "Converting angles", "π rad = 180°", "Trig"),
                Formula("trig2", "trigonometry", "Trigonometry", "sin(A ± B)", "sin(A ± B) = sin A cos B ± cos A sin B", "Expanding sum/difference of sine", "A, B = angles", "Trig"),
                Formula("trig3", "trigonometry", "Trigonometry", "cos(A ± B)", "cos(A ± B) = cos A cos B ∓ sin A sin B", "Notice the minus sign when adding!", "A, B = angles", "Trig"),
                Formula("trig4", "trigonometry", "Trigonometry", "sin 2x", "sin 2x = 2 sin x cos x = 2 tan x / (1 + tan²x)", "Double angle of sine", "x = angle", "Trig"),
                Formula("trig5", "trigonometry", "Trigonometry", "cos 2x", "cos 2x = cos²x - sin²x = 2cos²x - 1 = 1 - 2sin²x", "Double angle of cosine", "x = angle", "Trig"),
                Formula("trig6", "trigonometry", "Trigonometry", "Half Angle Trick", "1 - cos 2x = 2 sin²x,  1 + cos 2x = 2 cos²x", "Converting 1 ± cos into pure squares", "Key for calculus", "Trig")
            ),
            pitfalls = listOf(
                Pitfall(
                    mistake = "Writing sin(A + B) = sin A + sin B",
                    whyItHappens = "Treating sin like an algebraic variable that distributes.",
                    correctWay = "'sin' is a function, not a multiplier! Always use sin(A+B) = sin A cos B + cos A sin B."
                ),
                Pitfall(
                    mistake = "Sign mistake in cos(A + B)",
                    whyItHappens = "Assuming cos(A+B) has a '+' inside.",
                    correctWay = "Cosine is contrary: cos(A + B) has a MINUS: cos A cos B - sin A sin B."
                )
            ),
            memoryTricks = listOf(
                "Quadrant Signs: ASTC -> 'All Silver Tea Cups' (Quad I=All, II=Sin, III=Tan, IV=Cos).",
                "Cosine is contrary: plus turns to minus in cos(A+B)!"
            )
        ),

        Chapter(
            id = "complex_numbers",
            number = 3,
            title = "Complex Numbers & Quadratics",
            subtitle = "Unlocking solutions when D < 0 with 'i'",
            iconName = "complex",
            summary = "In Class 10, when b² - 4ac < 0 you wrote 'No real roots'. In Class 11, we introduce i = √(-1) to solve ANY quadratic equation.",
            whyItMatters = "Complex numbers bridge algebra and geometry. They are the heart of electrical engineering, physics, and advanced mathematics.",
            topics = listOf(
                Topic(
                    id = "complex_basics",
                    chapterId = "complex_numbers",
                    title = "The Imaginary Unit i and its Powers",
                    description = "i is defined as the solution to x² + 1 = 0, so i² = -1. Any higher power of i reduces by dividing exponent by 4.",
                    keyPoints = listOf(
                        "i¹ = i",
                        "i² = -1",
                        "i³ = i² × i = -i",
                        "i⁴ = 1",
                        "Rule: iⁿ = iʳ where r is the remainder when n is divided by 4!"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Evaluate i⁹⁹.",
                        hints = listOf("Divide 99 by 4 and find the remainder"),
                        steps = listOf(
                            "99 ÷ 4 = 24 with remainder 3 (since 4 × 24 = 96).",
                            "So i⁹⁹ = i⁹⁶⁺³ = (i⁴)²⁴ × i³.",
                            "= (1)²⁴ × (-i) = 1 × (-i) = -i."
                        ),
                        answer = "i⁹⁹ = -i"
                    )
                ),
                Topic(
                    id = "modulus_conjugate",
                    chapterId = "complex_numbers",
                    title = "Modulus & Conjugate of z = a + bi",
                    description = "A complex number has a real part a and imaginary part b. The conjugate z̄ flips the sign of b. The modulus |z| is its distance from origin.",
                    keyPoints = listOf(
                        "Conjugate: If z = a + bi, then z̄ = a - bi.",
                        "Modulus: |z| = √(a² + b²).",
                        "Key identity: z × z̄ = a² + b² = |z|².",
                        "Multiplicative inverse: z⁻¹ = z̄ / |z|²."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the multiplicative inverse of z = 3 + 4i.",
                        hints = listOf("Use formula z⁻¹ = z̄ / |z|²"),
                        steps = listOf(
                            "Identify a = 3, b = 4.",
                            "Conjugate z̄ = 3 - 4i.",
                            "|z|² = 3² + 4² = 9 + 16 = 25.",
                            "z⁻¹ = (3 - 4i) / 25 = 3/25 - (4/25)i."
                        ),
                        answer = "z⁻¹ = 3/25 - 4/25 i"
                    )
                ),
                Topic(
                    id = "quadratic_complex",
                    chapterId = "complex_numbers",
                    title = "Solving Quadratics with D < 0",
                    description = "When discriminant D = b² - 4ac < 0, √D = √( -|D| ) = i√|D|.",
                    keyPoints = listOf(
                        "Formula: x = (-b ± i√|D|) / (2a)",
                        "Roots always occur in conjugate pairs (a + bi and a - bi) when coefficients are real."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Solve x² + x + 1 = 0.",
                        hints = listOf("Identify a=1, b=1, c=1", "Calculate D = b² - 4ac"),
                        steps = listOf(
                            "a = 1, b = 1, c = 1.",
                            "D = b² - 4ac = 1² - 4(1)(1) = 1 - 4 = -3.",
                            "Since D = -3 < 0, √D = √(-3) = i√3.",
                            "x = (-1 ± i√3) / (2 × 1)."
                        ),
                        answer = "x = (-1 ± i√3)/2"
                    )
                )
            ),
            formulas = listOf(
                Formula("cx1", "complex_numbers", "Complex Numbers", "Powers of i", "i¹=i, i²=-1, i³=-i, i⁴=1", "Powers cycle every 4", "Divide exponent by 4", "Algebra"),
                Formula("cx2", "complex_numbers", "Complex Numbers", "Modulus", "|z| = √(a² + b²)", "Distance of z from origin in Argand plane", "z = a + bi", "Algebra"),
                Formula("cx3", "complex_numbers", "Complex Numbers", "Conjugate", "z̄ = a - bi", "Reflecting across real axis", "z = a + bi", "Algebra"),
                Formula("cx4", "complex_numbers", "Complex Numbers", "Quadratic Roots (D < 0)", "x = (-b ± i√|D|) / 2a", "Roots of ax² + bx + c = 0 when D < 0", "D = b² - 4ac", "Algebra")
            ),
            pitfalls = listOf(
                Pitfall(
                    mistake = "Writing √(-a) × √(-b) = √((-a)(-b)) = √(ab)",
                    whyItHappens = "The rule √(xy) = √x √y is ONLY valid when at least one of x or y is non-negative!",
                    correctWay = "Always convert to i first: √(-a) = i√a, √(-b) = i√b. Then (i√a)(i√b) = i²√(ab) = -√(ab)."
                ),
                Pitfall(
                    mistake = "Saying z₁ > z₂ for complex numbers",
                    whyItHappens = "Thinking complex numbers have an order like real numbers.",
                    correctWay = "Complex numbers CANNOT be ordered with '<' or '>'. You can only compare their moduli: |z₁| > |z₂|."
                )
            ),
            memoryTricks = listOf(
                "Remainder after dividing power by 4 gives answer: 1→i, 2→-1, 3→-i, 0→1.",
                "z times its conjugate is always a pure positive real number: z × z̄ = a² + b²."
            )
        ),

        Chapter(
            id = "permutations_combinations",
            number = 4,
            title = "Permutations & Combinations",
            subtitle = "Master the art of systematic counting without listing",
            iconName = "combinations",
            summary = "P&C answers: In how many ways can events occur? Permutation means ARRANGEMENT (order matters). Combination means SELECTION (order does not matter).",
            whyItMatters = "One of the most scoring chapters once you crack the simple rule of deciding whether order matters.",
            topics = listOf(
                Topic(
                    id = "counting_principle",
                    chapterId = "permutations_combinations",
                    title = "Fundamental Principles of Counting",
                    description = "Multiplication Principle: If event 1 happens in m ways and event 2 in n ways, both happen in m × n ways (AND). Addition Principle: If either can happen, m + n ways (OR).",
                    keyPoints = listOf(
                        "AND means MULTIPLY: both tasks happen together.",
                        "OR means ADD: either task 1 OR task 2 happens.",
                        "Factorial: n! = n × (n-1) × ... × 1. By convention 0! = 1."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "How many 3-digit numbers can be formed from digits 1, 2, 3, 4, 5 without repetition?",
                        hints = listOf("Hundreds place has 5 choices", "Tens place has 4 choices", "Units place has 3 choices"),
                        steps = listOf(
                            "Hundreds place: 5 choices (any of 1,2,3,4,5).",
                            "Tens place: 4 choices remaining.",
                            "Units place: 3 choices remaining.",
                            "Total = 5 × 4 × 3 = 60."
                        ),
                        answer = "60 numbers"
                    )
                ),
                Topic(
                    id = "p_vs_c",
                    chapterId = "permutations_combinations",
                    title = "Permutation vs Combination Decision",
                    description = "Learn the foolproof test: 'If I swap two items in my group, does it count as a DIFFERENT outcome?' If YES -> Permutation. If NO -> Combination.",
                    keyPoints = listOf(
                        "Permutation ⁿPᵣ = n! / (n - r)!  (Order / Arrangement / Rank / Positions)",
                        "Combination ⁿCᵣ = n! / [r! (n - r)!]  (Selection / Committee / Handshakes / Geometry)",
                        "Relation: ⁿPᵣ = r! × ⁿCᵣ",
                        "Key symmetry: ⁿCᵣ = ⁿCₙ₋ᵣ"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "From a class of 10 students, how many committees of 3 students can be chosen?",
                        hints = listOf("Does order of selection matter in a committee?", "Use ⁿCᵣ formula"),
                        steps = listOf(
                            "Selecting Student A, B, C is the exact same committee as C, B, A.",
                            "Order DOES NOT matter -> Use Combination ¹⁰C₃.",
                            "¹⁰C₃ = 10! / (3! × 7!) = (10 × 9 × 8) / (3 × 2 × 1).",
                            "= 720 / 6 = 120."
                        ),
                        answer = "120 committees"
                    )
                )
            ),
            formulas = listOf(
                Formula("pc1", "permutations_combinations", "P&C", "Permutation Formula", "ⁿPᵣ = n! / (n - r)!", "Arranging r items out of n distinct items", "n = total, r = chosen", "Combinatorics"),
                Formula("pc2", "permutations_combinations", "P&C", "Combination Formula", "ⁿCᵣ = n! / [r! (n - r)!]", "Selecting r items out of n items (no order)", "n = total, r = chosen", "Combinatorics"),
                Formula("pc3", "permutations_combinations", "P&C", "Symmetry Property", "ⁿCᵣ = ⁿCₙ₋ᵣ", "Simplifying large combinations like ¹⁰⁰C₉₈ = ¹⁰⁰C₂", "n, r integers", "Combinatorics"),
                Formula("pc4", "permutations_combinations", "P&C", "Pascal's Identity", "ⁿCᵣ + ⁿCᵣ₋₁ = ⁿ⁺¹Cᵣ", "Combining adjacent binomial coefficients", "Used in proofs", "Combinatorics")
            ),
            pitfalls = listOf(
                Pitfall(
                    mistake = "Using Permutation (ⁿPᵣ) when selecting a group",
                    whyItHappens = "Forgetting that selecting Alice then Bob gives the same group as Bob then Alice.",
                    correctWay = "Ask: 'Does order matter?' A committee of 3 has no order -> ⁿCᵣ. But electing President, VP, Secretary HAS order -> ⁿPᵣ!"
                ),
                Pitfall(
                    mistake = "Thinking 0! = 0",
                    whyItHappens = "Multiplying by zero in intuition.",
                    correctWay = "0! = 1 by definition (there is 1 way to arrange 0 items: do nothing!)."
                )
            ),
            memoryTricks = listOf(
                "P is for Positions (Order matters!). C is for Choose (Order doesn't matter!).",
                "ⁿCᵣ shortcut: ¹⁰C₃ = start from 10, write 3 descending numbers (10×9×8), divide by 3! (3×2×1)."
            )
        ),

        Chapter(
            id = "sequences_series",
            number = 5,
            title = "Sequences & Series (AP & GP)",
            subtitle = "Patterns, nth terms, and finite & infinite sums",
            iconName = "series",
            summary = "Arithmetic Progressions (AP) add a constant difference d. Geometric Progressions (GP) multiply by a constant ratio r.",
            whyItMatters = "One of the most intuitive and scoring chapters in Class 11. Used everywhere in finance, compound interest, and physics.",
            topics = listOf(
                Topic(
                    id = "ap_core",
                    chapterId = "sequences_series",
                    title = "Arithmetic Progression (AP)",
                    description = "Terms have a common difference: a, a+d, a+2d, ...",
                    keyPoints = listOf(
                        "n-th term: aₙ = a + (n - 1)d",
                        "Sum of n terms: Sₙ = (n/2)[2a + (n - 1)d] = (n/2)(a + l)",
                        "Arithmetic Mean between a and b: AM = (a + b) / 2"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the sum of first 20 terms of the AP: 3, 7, 11, 15, ...",
                        hints = listOf("Identify first term a and common difference d", "Use Sₙ = (n/2)[2a + (n-1)d]"),
                        steps = listOf(
                            "First term a = 3.",
                            "Common difference d = 7 - 3 = 4.",
                            "Number of terms n = 20.",
                            "S₂₀ = (20/2) × [2(3) + (20 - 1) × 4].",
                            "= 10 × [6 + 19 × 4] = 10 × [6 + 76] = 10 × 82 = 820."
                        ),
                        answer = "S₂₀ = 820"
                    )
                ),
                Topic(
                    id = "gp_core",
                    chapterId = "sequences_series",
                    title = "Geometric Progression (GP)",
                    description = "Terms multiply by common ratio r: a, ar, ar², ...",
                    keyPoints = listOf(
                        "n-th term: aₙ = a × rⁿ⁻¹",
                        "Sum of n terms: Sₙ = a(rⁿ - 1) / (r - 1)  (for r ≠ 1)",
                        "Sum of Infinite GP: S_∞ = a / (1 - r)  (valid ONLY when |r| < 1!)",
                        "Geometric Mean between a and b: GM = √(ab)"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the sum to infinity of 1 + 1/2 + 1/4 + 1/8 + ...",
                        hints = listOf("Identify a and common ratio r", "Check if |r| < 1", "Use S_∞ = a / (1 - r)"),
                        steps = listOf(
                            "First term a = 1.",
                            "Common ratio r = (1/2) / 1 = 1/2.",
                            "Since |1/2| < 1, the infinite sum converges!",
                            "S_∞ = 1 / (1 - 1/2) = 1 / (1/2) = 2."
                        ),
                        answer = "S_∞ = 2"
                    )
                )
            ),
            formulas = listOf(
                Formula("seq1", "sequences_series", "AP & GP", "AP nth term", "aₙ = a + (n - 1)d", "Finding any term in AP", "a = 1st term, d = diff", "Sequences"),
                Formula("seq2", "sequences_series", "AP & GP", "AP Sum", "Sₙ = (n/2)[2a + (n - 1)d]", "Finding sum of first n terms of AP", "l = last term", "Sequences"),
                Formula("seq3", "sequences_series", "AP & GP", "GP nth term", "aₙ = a rⁿ⁻¹", "Finding any term in GP", "r = common ratio", "Sequences"),
                Formula("seq4", "sequences_series", "AP & GP", "Infinite GP Sum", "S_∞ = a / (1 - r)", "Sum of endless GP when |r| < 1", "|r| < 1 required", "Sequences"),
                Formula("seq5", "sequences_series", "AP & GP", "AM ≥ GM Inequality", "(a + b)/2 ≥ √(ab)", "Crucial inequality for finding max/min", "a, b > 0", "Sequences")
            ),
            pitfalls = listOf(
                Pitfall(
                    mistake = "Using Infinite GP formula S = a/(1-r) when |r| ≥ 1",
                    whyItHappens = "Blindly plugging numbers into formula when r = 2 or r = -3.",
                    correctWay = "The sum to infinity ONLY exists when |r| < 1 (fraction between -1 and 1). If |r| ≥ 1, sum diverges to infinity!"
                ),
                Pitfall(
                    mistake = "Writing nth term of GP as a rⁿ instead of a rⁿ⁻¹",
                    whyItHappens = "Forgetting that the 1st term already has r⁰ = 1.",
                    correctWay = "1st term is a = a r⁰. 2nd term is a r¹. The power of r is always (n - 1)!"
                )
            ),
            memoryTricks = listOf(
                "AP adds: a, a+d, a+2d (power of d is n-1).",
                "GP multiplies: a, ar, ar² (power of r is n-1)."
            )
        ),

        Chapter(
            id = "straight_lines",
            number = 6,
            title = "Straight Lines",
            subtitle = "Slopes, intercepts, and distance formulas",
            iconName = "lines",
            summary = "A line is determined by either two points or one point and a slope. Master slope m = tan θ and standard line equations.",
            whyItMatters = "Forms the base of all 2D and 3D coordinate geometry, linear programming, and tangent lines in calculus.",
            topics = listOf(
                Topic(
                    id = "line_slope",
                    chapterId = "straight_lines",
                    title = "Slope of a Line (m)",
                    description = "Slope measures steepness: m = (y₂ - y₁) / (x₂ - x₁) = tan θ.",
                    keyPoints = listOf(
                        "Parallel lines have EQUAL slopes: m₁ = m₂.",
                        "Perpendicular lines have slopes that multiply to -1: m₁ × m₂ = -1 (or m₂ = -1/m₁).",
                        "Horizontal line (parallel to x-axis): slope = 0.",
                        "Vertical line (parallel to y-axis): slope is undefined."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the slope of a line perpendicular to line passing through (2, 3) and (4, 7).",
                        hints = listOf("First find slope m₁ of the given line", "Use m₁ × m₂ = -1"),
                        steps = listOf(
                            "m₁ = (7 - 3) / (4 - 2) = 4 / 2 = 2.",
                            "For perpendicular line, m₂ = -1 / m₁.",
                            "m₂ = -1 / 2."
                        ),
                        answer = "Perpendicular slope = -1/2"
                    )
                ),
                Topic(
                    id = "line_forms",
                    chapterId = "straight_lines",
                    title = "Equations of a Line & Distance Formula",
                    description = "Different forms of line equations and finding distance from a point to a line.",
                    keyPoints = listOf(
                        "Slope-Intercept form: y = mx + c (c is y-intercept).",
                        "Point-Slope form: y - y₁ = m(x - x₁).",
                        "Intercept form: x/a + y/b = 1 (a is x-intercept, b is y-intercept).",
                        "Distance from (x₀, y₀) to Ax + By + C = 0 is d = |Ax₀ + By₀ + C| / √(A² + B²)."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find distance from point (2, 1) to the line 3x + 4y - 5 = 0.",
                        hints = listOf("Use distance formula d = |Ax₀ + By₀ + C| / √(A² + B²)"),
                        steps = listOf(
                            "A = 3, B = 4, C = -5. Point is (x₀, y₀) = (2, 1).",
                            "Numerator = |3(2) + 4(1) - 5| = |6 + 4 - 5| = |5| = 5.",
                            "Denominator = √(3² + 4²) = √(9 + 16) = √25 = 5.",
                            "Distance d = 5 / 5 = 1."
                        ),
                        answer = "Distance = 1 unit"
                    )
                )
            ),
            formulas = listOf(
                Formula("line1", "straight_lines", "Straight Lines", "Slope Formula", "m = (y₂ - y₁) / (x₂ - x₁) = tan θ", "Slope given two points", "θ = angle with +ve x-axis", "Geometry"),
                Formula("line2", "straight_lines", "Straight Lines", "Point-Slope Form", "y - y₁ = m(x - x₁)", "Equation when point and slope are known", "m = slope, (x₁,y₁) = point", "Geometry"),
                Formula("line3", "straight_lines", "Straight Lines", "Perpendicular Slopes", "m₁ × m₂ = -1", "Condition for perpendicular lines", "m₁, m₂ = slopes", "Geometry"),
                Formula("line4", "straight_lines", "Straight Lines", "Point to Line Distance", "d = |Ax₁ + By₁ + C| / √(A² + B²)", "Shortest distance from point to line", "Ax+By+C=0", "Geometry")
            ),
            pitfalls = listOf(
                Pitfall(
                    mistake = "Forgetting absolute value in distance formula",
                    whyItHappens = "Distance can never be negative, but plugging into Ax+By+C might yield a negative number.",
                    correctWay = "Always put the numerator inside absolute value bars |Ax₀ + By₀ + C|."
                ),
                Pitfall(
                    mistake = "Confusing parallel (m₁ = m₂) with perpendicular (m₁m₂ = -1)",
                    whyItHappens = "Rushing through the question.",
                    correctWay = "Parallel = same tilt (m₁ = m₂). Perpendicular = flip fraction and flip sign (-1/m)."
                )
            ),
            memoryTricks = listOf(
                "Perpendicular slope is the 'negative reciprocal': flip upside down and change sign (+ to -).",
                "y = mx + c: m is Move (slope), c is Cross (y-intercept)!"
            )
        ),

        Chapter(
            id = "limits_derivatives",
            number = 7,
            title = "Limits & Derivatives",
            subtitle = "Your gateway to Calculus made visual & gentle",
            iconName = "calculus",
            summary = "Limits tell us what value a function gets close to. Derivatives measure instantaneous rate of change (slope of tangent).",
            whyItMatters = "Calculus accounts for almost 50% of Class 12 Maths marks. Grasping the basics here makes Class 12 feel 10x easier!",
            topics = listOf(
                Topic(
                    id = "limits_intro",
                    chapterId = "limits_derivatives",
                    title = "Evaluating Limits & Standard Forms",
                    description = "When direct substitution gives 0/0 (indeterminate form), factor, rationalize, or use standard limits.",
                    keyPoints = listOf(
                        "lim(x→a) [xⁿ - aⁿ] / [x - a] = n aⁿ⁻¹",
                        "lim(x→0) [sin x] / x = 1 (angle MUST be in radians!)",
                        "lim(x→0) [tan x] / x = 1",
                        "lim(x→0) [1 - cos x] / x = 0"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Evaluate lim(x→2) (x² - 4) / (x - 2).",
                        hints = listOf("Direct substitution gives (4-4)/(2-2) = 0/0", "Factor numerator x² - 4 = (x-2)(x+2)"),
                        steps = listOf(
                            "Direct substitution x = 2 gives 0/0 (indeterminate).",
                            "Factor numerator: x² - 4 = (x - 2)(x + 2).",
                            "Expression becomes: [(x - 2)(x + 2)] / (x - 2).",
                            "Since x approaches 2 but x ≠ 2, cancel (x - 2).",
                            "Now evaluate lim(x→2) (x + 2) = 2 + 2 = 4."
                        ),
                        answer = "Limit = 4"
                    )
                ),
                Topic(
                    id = "derivative_rules",
                    chapterId = "limits_derivatives",
                    title = "Core Derivative Rules",
                    description = "Derivative f'(x) gives rate of change. Master Power, Product, and Quotient rules.",
                    keyPoints = listOf(
                        "Power Rule: d/dx [xⁿ] = n xⁿ⁻¹",
                        "Constant Rule: d/dx [c] = 0",
                        "Product Rule: (uv)' = u'v + uv'",
                        "Quotient Rule: (u/v)' = (u'v - uv') / v² ('low d-high minus high d-low over low squared')",
                        "d/dx [sin x] = cos x,  d/dx [cos x] = -sin x"
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Find the derivative of f(x) = x³ sin(x).",
                        hints = listOf("This is a product of two functions u = x³ and v = sin(x)", "Use Product Rule (uv)' = u'v + uv'"),
                        steps = listOf(
                            "Let u = x³, so u' = 3x².",
                            "Let v = sin(x), so v' = cos(x).",
                            "Apply Product Rule: f'(x) = u'v + uv'.",
                            "= (3x²)(sin x) + (x³)(cos x).",
                            "= x² (3 sin x + x cos x)."
                        ),
                        answer = "f'(x) = x²(3 sin x + x cos x)"
                    )
                ),
                Topic(
                    id = "limits_continuity",
                    chapterId = "limits_derivatives",
                    title = "Intuition of Continuity & LHL = RHL",
                    description = "A function is continuous at x = a if you can draw its graph through x = a without lifting your pen. Mathematically: Left Hand Limit = Right Hand Limit = Value of Function at that point.",
                    keyPoints = listOf(
                        "LHL (Left Hand Limit): Value f(x) approaches from values smaller than a (x → a⁻).",
                        "RHL (Right Hand Limit): Value f(x) approaches from values larger than a (x → a⁺).",
                        "Existence of Limit: lim(x→a) f(x) exists ONLY when LHL = RHL.",
                        "Condition for Continuity: lim(x→a) f(x) = f(a)."
                    ),
                    exampleProblem = ExampleProblem(
                        question = "Is f(x) = 2x + 3 continuous at x = 1?",
                        hints = listOf("Calculate LHL when x approaches 1 from left", "Calculate RHL when x approaches 1 from right", "Compare with f(1)"),
                        steps = listOf(
                            "Step 1: Calculate f(1) = 2(1) + 3 = 5.",
                            "Step 2: LHL = lim(x→1⁻) (2x + 3) = 2(1) + 3 = 5.",
                            "Step 3: RHL = lim(x→1⁺) (2x + 3) = 2(1) + 3 = 5.",
                            "Step 4: Since LHL = RHL = f(1) = 5, the function has no break or jump."
                        ),
                        answer = "Yes, f(x) is continuous at x = 1 because LHL = RHL = f(1) = 5."
                    )
                )
            ),
            formulas = listOf(
                Formula("lim0", "limits_derivatives", "Limits & Derivatives", "Continuity Condition", "lim(x→a⁻) f(x) = lim(x→a⁺) f(x) = f(a)", "Testing if a function is continuous without graph breaks", "LHL = RHL = f(a)", "Calculus"),
                Formula("lim1", "limits_derivatives", "Limits & Derivatives", "Power Limit", "lim(x→a) [xⁿ - aⁿ] / [x - a] = n aⁿ⁻¹", "Evaluating algebraic limits in 0/0 form", "a = constant, n = rational", "Calculus"),
                Formula("lim2", "limits_derivatives", "Limits & Derivatives", "Sine Limit", "lim(x→0) (sin x / x) = 1", "Trigonometric limit fundamental", "x in radians", "Calculus"),
                Formula("lim3", "limits_derivatives", "Limits & Derivatives", "Power Rule", "d/dx [xⁿ] = n xⁿ⁻¹", "Differentiating any polynomial term", "n can be any real number", "Calculus"),
                Formula("lim4", "limits_derivatives", "Limits & Derivatives", "Product Rule", "d/dx [u · v] = u'v + uv'", "Differentiating two multiplied functions", "u, v functions of x", "Calculus"),
                Formula("lim5", "limits_derivatives", "Limits & Derivatives", "Quotient Rule", "d/dx [u / v] = (u'v - uv') / v²", "Differentiating a fraction", "v ≠ 0", "Calculus")
            ),
            pitfalls = listOf(
                Pitfall(
                    mistake = "Applying Product Rule as (uv)' = u' × v'",
                    whyItHappens = "Assuming derivative distributes over multiplication just like addition.",
                    correctWay = "Derivative DOES NOT simply multiply! Always use: u'v + uv'."
                ),
                Pitfall(
                    mistake = "Forgetting minus in d/dx [cos x]",
                    whyItHappens = "Confusing sign with d/dx [sin x].",
                    correctWay = "Rule: ALL trigonometric functions starting with 'c' (cos, cot, csc) have a NEGATIVE derivative! (d/dx cos x = -sin x)."
                )
            ),
            memoryTricks = listOf(
                "All 'Co-' functions have negative derivatives: cos → -sin, cot → -csc², csc → -csc·cot!",
                "Quotient rule rhyme: 'Low d-high minus high d-low, all over the square of what's below!'"
            )
        )
    )

    val sampleQuizzes: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "q1",
            chapterId = "trigonometry",
            chapterName = "Trigonometry",
            question = "In which quadrant are both sine and cosine negative, but tangent is positive?",
            options = listOf("Quadrant I", "Quadrant II", "Quadrant III", "Quadrant IV"),
            correctIndex = 2,
            hint = "Think about the ASTC rule: All - Silver - Tea - Cups.",
            explanationSteps = listOf(
                "Quadrant I: ALL functions are positive.",
                "Quadrant II: Only Sine (and Csc) are positive.",
                "Quadrant III: TEA -> Tangent (and Cot) are positive, while Sine and Cosine are NEGATIVE!",
                "Quadrant IV: Only Cosine (and Sec) are positive."
            )
        ),
        QuizQuestion(
            id = "q2",
            chapterId = "complex_numbers",
            chapterName = "Complex Numbers",
            question = "What is the value of i²⁴?",
            options = listOf("i", "-1", "-i", "1"),
            correctIndex = 3,
            hint = "Divide the exponent 24 by 4 and check the remainder.",
            explanationSteps = listOf(
                "Powers of i repeat in cycles of 4: i¹ = i, i² = -1, i³ = -i, i⁴ = 1.",
                "Divide 24 by 4: 24 ÷ 4 = 6 with remainder 0.",
                "Since remainder is 0, i²⁴ = (i⁴)⁶ = 1⁶ = 1."
            )
        ),
        QuizQuestion(
            id = "q3",
            chapterId = "permutations_combinations",
            chapterName = "Permutations & Combinations",
            question = "In how many ways can 5 students be arranged in a straight row for a photo?",
            options = listOf("25", "60", "120", "20"),
            correctIndex = 2,
            hint = "Order matters when arranging in a row. How many choices for each spot?",
            explanationSteps = listOf(
                "Arranging 5 items in 5 positions means order matters -> Permutation.",
                "Formula is 5! (5 factorial).",
                "5! = 5 × 4 × 3 × 2 × 1 = 120."
            )
        ),
        QuizQuestion(
            id = "q4",
            chapterId = "sequences_series",
            chapterName = "Sequences & Series",
            question = "What is the common difference 'd' of the AP: 12, 7, 2, -3, ...?",
            options = listOf("5", "-5", "7", "-7"),
            correctIndex = 1,
            hint = "Formula for d is always second term minus first term: a₂ - a₁.",
            explanationSteps = listOf(
                "d = a₂ - a₁ = 7 - 12 = -5.",
                "Notice the numbers are decreasing, so the difference must be NEGATIVE."
            )
        ),
        QuizQuestion(
            id = "q5",
            chapterId = "limits_derivatives",
            chapterName = "Limits & Derivatives",
            question = "What is the derivative of f(x) = 5x⁴ - 7x + 9?",
            options = listOf("20x³ - 7", "20x⁴ - 7", "5x³ - 7", "20x³ - 7x"),
            correctIndex = 0,
            hint = "Use power rule d/dx [xⁿ] = n xⁿ⁻¹ on each term. Derivative of a constant is 0.",
            explanationSteps = listOf(
                "Term 1: d/dx [5x⁴] = 5 × (4x³) = 20x³.",
                "Term 2: d/dx [-7x] = -7 × (1) = -7.",
                "Term 3: d/dx [9] = 0 (constant derivative is zero).",
                "Combining gives: 20x³ - 7."
            )
        ),
        QuizQuestion(
            id = "q6",
            chapterId = "sets_relations",
            chapterName = "Sets & Functions",
            question = "If a set S has 4 elements, how many subsets does it have in total?",
            options = listOf("8", "12", "16", "24"),
            correctIndex = 2,
            hint = "Recall the formula for total subsets: 2ⁿ where n is the number of elements.",
            explanationSteps = listOf(
                "Number of elements n = 4.",
                "Total number of subsets = 2ⁿ = 2⁴.",
                "2⁴ = 2 × 2 × 2 × 2 = 16."
            )
        ),
        QuizQuestion(
            id = "q7",
            chapterId = "straight_lines",
            chapterName = "Straight Lines",
            question = "If a line has slope m = 3, what is the slope of any line perpendicular to it?",
            options = listOf("3", "-3", "1/3", "-1/3"),
            correctIndex = 3,
            hint = "Perpendicular lines satisfy m₁ × m₂ = -1. Take negative reciprocal.",
            explanationSteps = listOf(
                "Given m₁ = 3.",
                "For perpendicular lines, m₁ × m₂ = -1.",
                "3 × m₂ = -1  =>  m₂ = -1/3."
            )
        )
    )
}
