package org.logout.notifications.telegram.infobipbot

import org.junit.Test

/**
 * Created by sulion on 11.05.17.
 */
class InfobipTelegramBotTest {
    @Test
    fun sendCompositeMessages() {
        val lines = listOf("abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ",
                "abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca abc abc cab bca ")

        var temp = StringBuilder()
        val results = ArrayList<String>()
        for (line in lines) {
            if (temp.length + line.length > 300) {
                results.add(temp.toString())
                temp = StringBuilder()
            }
            temp.append(line)
        }
        print(results.size)
    }

}