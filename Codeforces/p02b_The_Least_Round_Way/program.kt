import java.util.StringTokenizer

const val INF = 1_000_000_000

fun countTwos(num: Int): Int {
    var number = num
    var count = 0

    while (number != 0 && number % 2 == 0) {
        number /= 2
        count++
    }

    return count
}

fun countFives(num: Int): Int {
    var number = num
    var count = 0

    while (number != 0 && number % 5 == 0) {
        number /= 5
        count++
    }

    return count
}

// Solve DP for either factor 2 or factor 5
data class DPResult(
    val cost: Int,
    val path: String
)

fun solveDP(cost: Array<IntArray>, n: Int): DPResult {

    val dp = Array(n) { IntArray(n) { INF } }
    val parent = Array(n) { CharArray(n) }

    dp[0][0] = cost[0][0]

    // Fill the DP table
    for (i in 0 until n) {
        for (j in 0 until n) {

            if (i == 0 && j == 0) {
                continue
            }

            // From top
            if (i > 0 && dp[i - 1][j] != INF) {
                val newCost = dp[i - 1][j] + cost[i][j]

                if (newCost < dp[i][j]) {
                    dp[i][j] = newCost
                    parent[i][j] = 'D'
                }
            }

            // From left
            if (j > 0 && dp[i][j - 1] != INF) {
                val newCost = dp[i][j - 1] + cost[i][j]

                if (newCost < dp[i][j]) {
                    dp[i][j] = newCost
                    parent[i][j] = 'R'
                }
            }
        }
    }

    // Reconstruct path
    val path = StringBuilder()

    var row = n - 1
    var col = n - 1

    while (row != 0 || col != 0) {

        val move = parent[row][col]

        path.append(move)

        if (move == 'D') {
            // We came from the cell above
            row--
        } else if (move == 'R') {
            // We came from the cell on the left
            col--
        }
    }

    // Reverse the path
    val finalPath = path.reverse().toString()

    return DPResult(
        dp[n - 1][n - 1],
        finalPath
    )
}

fun main() {

    val n = readLine()!!.trim().toInt()

    val matrix = Array(n) { IntArray(n) }
    val twos = Array(n) { IntArray(n) }
    val fives = Array(n) { IntArray(n) }

    var zeroRow = -1
    var zeroCol = -1

    // Input matrix and calculate factors
    for (i in 0 until n) {
        val st = StringTokenizer(readLine()!!)

        for (j in 0 until n) {

            matrix[i][j] = st.nextToken().toInt()

            if (matrix[i][j] == 0) {

                zeroRow = i
                zeroCol = j

                // Don't allow normal DP to use zero
                twos[i][j] = INF
                fives[i][j] = INF

            } else {

                twos[i][j] = countTwos(matrix[i][j])
                fives[i][j] = countFives(matrix[i][j])
            }
        }
    }

    // Find the best path for factors 2 and 5
    val answer2 = solveDP(twos, n)
    val answer5 = solveDP(fives, n)

    var bestZeros: Int
    var bestPath: String

    // Choose the better of the two DP solutions
    if (answer2.cost < answer5.cost) {
        bestZeros = answer2.cost
        bestPath = answer2.path
    } else {
        bestZeros = answer5.cost
        bestPath = answer5.path
    }

    // If zero exists and normal DP gives more than 1 trailing zero
    if (zeroRow != -1 && bestZeros > 1) {

        bestZeros = 1
        val path = StringBuilder()

        // Move down to zero's row
        for (i in 0 until zeroRow) {
            path.append('D')
        }

        // Move right to zero's column
        for (i in 0 until zeroCol) {
            path.append('R')
        }

        // Move down to the bottom
        for (i in zeroRow until n - 1) {
            path.append('D')
        }

        // Move right to the rightmost column
        for (i in zeroCol until n - 1) {
            path.append('R')
        }

        bestPath = path.toString()
    }

    println(bestZeros)
    println(bestPath)
}