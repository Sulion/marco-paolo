package org.logout.notifications.telegram.bot.commands

import org.logout.notifications.telegram.data.events.EventRegistry
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Chat
import org.telegram.telegrambots.api.objects.User
import org.telegram.telegrambots.bots.AbsSender
import org.telegram.telegrambots.bots.commands.BotCommand
import org.telegram.telegrambots.exceptions.TelegramApiException
import org.telegram.telegrambots.logging.BotLogger
import java.util.*


val COMMAND_NAME = "staima"
val COMMAND_DESCRIPTION = """Gets the next event
""".trimIndent()

val LOGTAG = "STAIMACOMMAND"

class StaImaGetNextEventCommand(val events: EventRegistry) : BotCommand(COMMAND_NAME, COMMAND_DESCRIPTION) {
    override fun execute(absSender: AbsSender, user: User?, chat: Chat, arguments: Array<String>?) {
        val next = events.data.sortedBy { it.startDate }.find { it.startDate.after(Date()) }
        val answerText = if(next != null) {
            """
            Your next event is ${next.eventName} by ${next.performerName} at ${next.startDate} on ${next.trackName} track. Get ready!
            """
        } else {
            "You don't seem to have anything planned. Enjoy your spare time!"
        }
        try {
            absSender.sendMessage(SendMessage().apply {
                chatId = chat.id.toString()
                text = answerText
            })
        } catch (e: TelegramApiException) {
            BotLogger.error(LOGTAG, e)
        }

    }
}