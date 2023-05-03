package tictactoe

fun main() {
    val startInput = generateStartInput()
    val playingField = generateMutableList(startInput)
    gridPrint(playingField)

    var state = "X"
    var count = 0
    while (count <= 9) {
        if (count >= 3 && checkResult(playingField, count)) {
            break
        }

        val option = readln().trim().split(" ")
        val isValid = checkValidOption(option, playingField)

        if (!isValid) continue

        changeField(state, option, playingField)
        state = if (state == "X") "O" else "X"
        count += 1
    }
}

fun changeField(state: String, option: List<String>, list: MutableList<MutableList<String>>) {
    val valX = option[0].toInt() - 1
    val valY = option[1].toInt() - 1
    list[valX][valY] = state

    gridPrint(list)
}

fun checkValidOption(option: List<String>, list: MutableList<MutableList<String>>): Boolean {
    if (option.size != 2) {
        println("You should enter numbers!")
        return false
    }

    val valX = option[0].toIntOrNull()
    val valY = option[1].toIntOrNull()

    if (valX == null || valY == null) {
        println("You should enter numbers!")
        return false
    }

    if (valX !in 1..3 || valY !in 1..3) {
        println("Coordinates should be from 1 to 3!")
        return false
    }

    if (list[valX - 1][valY - 1] != "_") {
        println("This cell is occupied! Choose another one!")
        return false
    }

    return true
}


/**
 * checkResult
 */

fun checkResult(list: MutableList<MutableList<String>>, count: Int): Boolean {
    val endedGameResult = endedGame(list)
    if (endedGameResult == "X" || endedGameResult == "O") {
        println("$endedGameResult wins")
        return true
    }

    if (count == 9) {
        println("Draw")
        return true
    }

    return false
}

fun endedGame(list: MutableList<MutableList<String>>): String {
    val rowChecked = checkRow(list)
    if (rowChecked == "X" || rowChecked == "O") {
        return rowChecked
    }

    val columnCheck = checkColumn(list)
    if (columnCheck == "X" || columnCheck == "O") {
        return columnCheck
    }

    return checkDiagonal(list)
}

fun checkRow(list: MutableList<MutableList<String>>): String {
    var triple = ""
    for (row in list) {
        val firstElementInRow = row[0]
        if (firstElementInRow == "_") continue

        val checkSameElements = row.all { firstElementInRow == it }
        if (checkSameElements) {
            triple = firstElementInRow
            return triple
        }
    }

    return triple
}

fun checkColumn(list: MutableList<MutableList<String>>): String {
    var triple = ""
    for (column in list.indices) {
        val mutableColumn = mutableListOf<String>()
        for (index in list.indices) {
            mutableColumn.add(list[index][column])
        }

        val firstElementInColumn = mutableColumn[0]
        if (firstElementInColumn == "_") continue

        val checkSameElements = mutableColumn.all { firstElementInColumn == it }
        if (checkSameElements) {
            triple = firstElementInColumn
            break
        }
    }
    return triple
}

fun checkDiagonal(list: MutableList<MutableList<String>>): String {
    var triple = ""
    for (diagonal in 0..1) {
        val mutableDiagonal = mutableListOf<String>()
        if (diagonal == 0) {
            mutableDiagonal.add(list[0][0])
            mutableDiagonal.add(list[1][1])
            mutableDiagonal.add(list[2][2])
        } else {
            mutableDiagonal.add(list[0][2])
            mutableDiagonal.add(list[1][1])
            mutableDiagonal.add(list[2][0])
        }

        val firstElementInDiagonal = mutableDiagonal[0]
        if (firstElementInDiagonal == "_") continue

        val checkSameElements = mutableDiagonal.all { firstElementInDiagonal == it }
        if (checkSameElements) {
            triple = firstElementInDiagonal
            break
        }
    }

    return triple
}


/**
* START AND PRINT FUNCTIONS
*/

fun generateMutableList(array: Array<String>): MutableList<MutableList<String>>  {
    val mutableList: MutableList<MutableList<String>> = mutableListOf()

    for (index in array.indices step 3) {
        val row: MutableList<String> = mutableListOf()
        row.add(array[index])
        row.add(array[index + 1])
        row.add(array[index + 2])

        mutableList.add(row)
    }

    return mutableList
}

fun generateStartInput(): Array<String> {
    var field = ""
    repeat(9) {
        field += "_"
    }
    return field.map { it.toString() }.toTypedArray()
}

fun gridPrint(list: MutableList<MutableList<String>>) {
    printDashesLine()

    for (index in list.indices) {
        printFirstBorder()
        printElement(list[index][0])
        printElement(list[index][1])
        printElement(list[index][2])
        printLastBorder()
    }

    printDashesLine()
}

fun printDashesLine() = println("---------")

fun printFirstBorder() = print("| ")

fun printElement(item: String) = print("$item ")

fun printLastBorder() = println("|")