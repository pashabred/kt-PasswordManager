package com.example.passlin

import java.util.Random

class PasswordGenerator {
companion object {
    fun generatePassword(length: Int = 8): String {
        val symbols = listOf(
            "~",
            "`",
            "!",
            "@",
            "#",
            "$",
            "%",
            "^",
            "&",
            "*",
            "(",
            ")",
            "_",
            "-",
            "+",
            "=",
            "{",
            "[",
            "}",
            "]",
            "|",
            "\\",
            ":",
            ";",
            "\"",
            "'",
            "<",
            ",",
            ">",
            ".",
            "?",
            "/",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "0",
            "q",
            "w",
            "e",
            "r",
            "t",
            "y",
            "u",
            "i",
            "o",
            "p",
            "l",
            "k",
            "j",
            "h",
            "g",
            "f",
            "d",
            "s",
            "a",
            "z",
            "x",
            "c",
            "v",
            "b",
            "n",
            "m",
            "Q",
            "W",
            "E",
            "R",
            "T",
            "Y",
            "U",
            "I",
            "O",
            "P",
            "L",
            "K",
            "J",
            "H",
            "G",
            "F",
            "D",
            "S",
            "A",
            "Z",
            "X",
            "C",
            "V",
            "B",
            "N",
            "M"
        )
        var password = ""
        for (i in 0..length) {
            password += random(symbols)
        }
        return password
    }


    private fun random(list: List<String>): String {
        val rand = Random()
        return list[rand.nextInt(list.size)]
    }
}
}