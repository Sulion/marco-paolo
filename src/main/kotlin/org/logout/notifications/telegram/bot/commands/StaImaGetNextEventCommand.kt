package org.logout.notifications.telegram.bot.commands

import org.logout.notifications.telegram.bot.processor.StaImaNextEventProcessor
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Chat
import org.telegram.telegrambots.api.objects.User
import org.telegram.telegrambots.bots.AbsSender
import org.telegram.telegrambots.bots.commands.BotCommand
import org.telegram.telegrambots.exceptions.TelegramApiException
import org.telegram.telegrambots.logging.BotLogger


val COMMAND_NAME = "staima"
val COMMAND_DESCRIPTION = """Gets the next event
""".trimIndent()

val LOGTAG = "STAIMACOMMAND"

class StaImaGetNextEventCommand(val processor: StaImaNextEventProcessor) : BotCommand(COMMAND_NAME, COMMAND_DESCRIPTION) {
    override fun execute(absSender: AbsSender, user: User?, chat: Chat, arguments: Array<String>?) {

        try {
            absSender.sendMessage(SendMessage().apply {
                chatId = chat.id.toString()
                text = processor.onMessage(arguments).joinToString(separator = "\n")
            })
        } catch (e: TelegramApiException) {
            BotLogger.error(LOGTAG, e)
        }

    }
}