package org.logout.notifications.telegram.infobipbot

import org.logout.notifications.telegram.bot.processor.StaImaNextEventProcessor
import org.logout.notifications.telegram.data.entities.InfobipIncomingMessage
import org.logout.notifications.telegram.data.entities.InfobipIncomingPackage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InfobipTelegramBot @Autowired constructor(private val taskScheduler: TaskScheduler,
                                                private val infobipTelegramService: InfobipTelegramService,
                                                private val staimaProcessor: StaImaNextEventProcessor) {
    companion object {
        val log = LoggerFactory.getLogger(InfobipTelegramBot::class.java)
    }

    @PostConstruct
    fun schedulePolling() {
        log.info("Scheduling message poll")
        val scheduledIncoming = taskScheduler.scheduleWithFixedDelay(
                { onMessages(infobipTelegramService.receiveAllmessages()) },
                3000)
    }

    fun onMessages(incomingPackage: InfobipIncomingPackage) {
        log.info("Took {} messages", incomingPackage.messageCount)
        if (incomingPackage.messageCount == 0)
            return
        incomingPackage.results.forEach { onSingleMessage(it) }
    }

    fun onSingleMessage(message: InfobipIncomingMessage) {
        if (message.message.type == "TEXT") {
            val text = message.message.text
            when {
                text.startsWith("/staima") ||
                        text.startsWith("/whatsup") ||
                        text.startsWith("/whazup") ||
                        text.startsWith("/чотамухохлов") ->
                    infobipTelegramService.sendSingleMessage(
                            staimaProcessor.onMessage(
                                    text.split(Regex("\\s")).drop(1/*/staima*/).toTypedArray()),
                            message.from)
                else -> infobipTelegramService.sendSingleMessage(message.message.text, message.from)
            }
        }
    }

    fun sendToEveryone(messageText: String) {
        infobipTelegramService.fetchUsers().users.forEach {
            infobipTelegramService.sendSingleMessage(messageText, it.key)
        }
    }
}
