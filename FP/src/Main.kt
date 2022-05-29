fun buildMenu():String {
    return "1-> Start New Game;\n0-> Exit Game;\n"
}

fun checkName(number: String): Boolean {
    if (number.length < 3) {
        return false
    }

    var count = 0
    var space = -1
    var lowerCase = 3
    while(count < number.length) {
        if (number[count] == ' ') {
            space = count
        } else if ((number[count].toInt() !in(65..90)) && count != space && count != space + 1 && count != 0) {
            lowerCase += 1
        }
        count++
    }

    return (number != null && (number[0].toInt() in(65..90)) && (number[space + 1].toInt() in(65..90)) && lowerCase == number.length)
}

fun checkIsNumber(number: String): Boolean {
    val num = number.toIntOrNull() ?: false
    return num != false
}

fun buildBoard(numColumns: Int, numLines: Int, showLegend: Boolean = false, showPieces: Boolean = false): String {
    val esc: String = Character.toString(27.toChar())
    val startBlue = "$esc[30;44m"
    val startGrey = "$esc[30;47m"
    val startWhite = "$esc[30;30m"
    val end = "$esc[0m"

    var tabuleiro = ""

    var columns = numColumns
    var lines = numLines
    val abc = "ABCDEFGHIJKLMNOPQRSTUBXWYZ"

    if (showLegend) { lines += 2 }

    var num = 1
    var dark = 0
    if (showLegend && numColumns % 2 == 0) { dark = 1 }

    while (lines > 0) {
        if (showLegend) { columns = numColumns + 2 } else { columns = numColumns }
        var count = 0
        while (columns > 0) {
            if (((columns == numColumns + 2) || (columns == 1)) && (lines == numLines + 2) && showLegend) {
                tabuleiro += "$startBlue   $end"
            } else if ((lines == 1 || columns == 1) && showLegend) {
                tabuleiro += "$startBlue   $end"
            } else if(showLegend && lines == numLines + 2 ) {
                tabuleiro += "$startBlue ${abc[count]} $end"
                count++
            } else if(showLegend && ((columns == numColumns + 2)) && ((lines < numLines + 2) && lines > 1)){
                tabuleiro += "$startBlue $num $end"
                num++
            } else {
                if (((showLegend && (lines == (numLines + 1))) || (!showLegend && (lines == (numLines)))) && showPieces) {
                    if (dark == 1) {
                        tabuleiro += "$startGrey \u265F $end"
                        dark--
                    } else {
                        tabuleiro += "$startWhite \u265F $end"
                        dark++
                    }
                }else if (((showLegend && (lines == 2)) || (!showLegend && (lines == 1))) && showPieces) {
                    if (dark == 1) {
                        tabuleiro += "$startGrey \u2659 $end"
                        dark--
                    } else {
                        tabuleiro += "$startWhite \u2659 $end"
                        dark++
                    }
                }else if (dark == 1) {
                    tabuleiro += "$startGrey   $end"
                    dark--
                } else {
                    tabuleiro += "$startWhite   $end"
                    dark++
                }
            }
            columns--
        }
        tabuleiro += "\n"
        lines--
        if (numColumns % 2 == 0 && dark == 0) { dark++ } else if (numColumns % 2 == 0 && dark == 1) { dark-- }
    }
    return tabuleiro
}

fun showChessLegendOrPieces(message: String): Boolean? {
    return if (message == "Y" || message == "y") {
        true
    } else if (message == "N" || message == "n") {
        false
    } else {
        null
    }
}


fun main() {
    val error = "Invalid response."
    var choice: Int?

    println("Welcome to the Chess Board Game!")
    do {
        do {
            println(buildMenu())
            choice = readLine()!!.toIntOrNull() ?: 2

            if (choice == 0) {
                return
            }
        } while (choice !in(0..1))

        var firstPlayer = ""
        var secondPlayer = ""
        do {
            println("First player name?\n")
            firstPlayer = readLine()!!
            if (!checkName(firstPlayer)) {
                println(error)
            }
        } while (!checkName(firstPlayer))

        do {
            println("Second player name?\n")
            secondPlayer = readLine()!!
            if (!checkName(secondPlayer)) {
                println(error)
            }
        } while (!checkName(secondPlayer))

        var columns = ""
        var lines = ""
        do {
            println("How many chess columns?\n")
            columns = readLine()!!
            if (!checkIsNumber(columns) || columns.toInt() < 5) {
                println(error)
            }
        } while (!checkIsNumber(columns) || columns.toInt() < 5)

        do {
            println("How many chess lines?\n")
            lines = readLine()!!
            if (!checkIsNumber(lines) || lines.toInt() < 5) {
                println(error)
            }
        } while (!checkIsNumber(lines) || lines.toInt() < 5)

        var showLegend = false
        var showPieces = false
        var opt: String
        do {
            println("Show legend (y/n)?\n")
            opt = readLine()!!
            if (showChessLegendOrPieces(opt) == null) {
                println(error)
            } else {
                showLegend = showChessLegendOrPieces(opt) ?: false
            }
        } while (showChessLegendOrPieces(opt) == null)

        do {
            println("Show pieces (y/n)?\n")
            opt = readLine()!!

            if (showChessLegendOrPieces(opt) == null) {
                println(error)
            } else {
                showPieces = showChessLegendOrPieces(opt) ?: false
            }
        } while (showChessLegendOrPieces(opt) == null)

        println(buildBoard(columns.toInt(), lines.toInt(), showLegend, showPieces))
    } while(true)

}