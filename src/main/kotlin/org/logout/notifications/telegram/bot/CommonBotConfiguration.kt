package org.logout.notifications.telegram.bot

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.logout.notifications.telegram.bot.processor.DissentProcessor
import org.logout.notifications.telegram.bot.processor.HelpProcessor
import org.logout.notifications.telegram.bot.processor.StaImaNextEventProcessor
import org.logout.notifications.telegram.bot.processor.StartProcessor
import org.logout.notifications.telegram.data.events.EventRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
open class CommonBotConfiguration {

    @Value("\${data.event.fileName}")
    lateinit var eventFileName: String


    @Bean open fun eventRegistry() =
            EventRegistry(FileInputStream(eventFileName), jacksonObjectMapper())

    @Bean open fun staImaNextEventProcessor(eventRegistry: EventRegistry) =
            StaImaNextEventProcessor(eventRegistry)

    @Bean open fun helpProcessor() = HelpProcessor()
    @Bean open fun startProcessor() = StartProcessor()
    @Bean open fun dissentProcessor() = DissentProcessor()

}
