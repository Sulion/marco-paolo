package org.logout.notifications.telegram.infobipbot

import org.logout.notifications.telegram.data.entities.InfobipIncomingMessage
import org.logout.notifications.telegram.data.entities.InfobipIncomingPackage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InfobipTelegramBot @Autowired constructor(val taskScheduler: TaskScheduler,
                                                val infobipTelegramService: InfobipTelegramService) {
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
        if (message.message.type == "TEXT")
            infobipTelegramService.sendSingleMessage(message.message.text, message.from)
    }
}
