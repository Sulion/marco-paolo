package org.logout.notifications.telegram.bot

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot


class MarcoPaoloBot(val token: String): TelegramLongPollingCommandBot() {
    override fun getBotUsername(): String {
        return "marco_paolo_bot"
    }

    override fun getBotToken(): String {
        return token

    }

    override fun processNonCommandUpdate(update: Update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.message.hasText()) {
            val text = update.message.text.replace(" бот", " человек").replace(" bot", " human")
            val message = SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.message.chatId)
                    .setText(text)
                sendMessage(message) // Call method to send the message
        }
    }

}