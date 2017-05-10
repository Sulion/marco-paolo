package org.logout.notifications.telegram.bot.commands

import org.telegram.telegrambots.api.objects.Chat
import org.telegram.telegrambots.api.objects.User
import org.telegram.telegrambots.bots.AbsSender
import org.telegram.telegrambots.bots.commands.BotCommand
import org.telegram.telegrambots.logging.BotLogger
import org.telegram.telegrambots.exceptions.TelegramApiException
import org.telegram.telegrambots.api.methods.send.SendMessage



val COMMAND_NAME = "staima"
val COMMAND_DESCRIPTION = """Gets the next event
""".trimIndent()

val LOGTAG = "STAIMACOMMAND"

class StaImaGetNextEventCommand: BotCommand(COMMAND_NAME, COMMAND_DESCRIPTION) {
    override fun execute(absSender: AbsSender, user: User?, chat: Chat, arguments: Array<String>?) {
        val answer = SendMessage()
        answer.chatId = chat.id.toString()
        answer.text = "Your events are coming soon, but you still have to wait"

        try {
            absSender.sendMessage(answer)
        } catch (e: TelegramApiException) {
            BotLogger.error(LOGTAG, e)
        }

    }
}