package org.logout.notifications.telegram.infobipbot

import org.logout.notifications.telegram.bot.processor.HelpProcessor
import org.logout.notifications.telegram.bot.processor.StaImaNextEventProcessor
import org.logout.notifications.telegram.data.entities.InfobipIncomingMessage
import org.logout.notifications.telegram.data.entities.InfobipIncomingPackage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import javax.annotation.PostConstruct

@Component
class InfobipTelegramBot @Autowired constructor(private val taskScheduler: TaskScheduler,
                                                private val infobipTelegramService: InfobipTelegramService,
                                                private val staimaProcessor: StaImaNextEventProcessor,
                                                private val helpProcessor: HelpProcessor) {
    companion object {
        val log = LoggerFactory.getLogger(InfobipTelegramBot::class.java)
    }

    val executor = Executors.newFixedThreadPool(4)

    @PostConstruct
    fun schedulePolling() {
        log.info("Scheduling message poll")
        val scheduledIncoming = taskScheduler.scheduleWithFixedDelay(
                { onMessages(infobipTelegramService.receiveAllmessages()) },
                3000)
    }

    fun onMessages(incomingPackage: InfobipIncomingPackage) {
        log.trace("Took {} messages", incomingPackage.messageCount)
        if (incomingPackage.messageCount == 0)
            return
        incomingPackage.results.forEach { executor.submit { onSingleMessage(it) } }
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
                            staimaProcessor.onMessage(args(text)),
                            message.from)
                text.startsWith("/help") -> infobipTelegramService.sendSingleMessage(
                        helpProcessor.onMessage(args(text)),
                        message.from)
                else -> infobipTelegramService.sendSingleMessage(message.message.text, message.from)
            }
        }
    }

    private fun args(str: String) =
            str.split(Regex("\\s")).drop(1/*/staima*/).toTypedArray()

    fun sendToEveryone(messageText: String) {
        infobipTelegramService.fetchUsers().users.forEach {
            infobipTelegramService.sendSingleMessage(messageText, it.key)
        }
    }
}
