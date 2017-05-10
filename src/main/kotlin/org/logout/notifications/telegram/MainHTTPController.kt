package org.logout.notifications.telegram

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.logout.notifications.telegram.bot.MarcoPaoloBot
import org.logout.notifications.telegram.bot.commands.StaImaGetNextEventCommand
import org.logout.notifications.telegram.data.events.EventRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import java.io.FileInputStream
import javax.annotation.PostConstruct


@RestController
open class MainHTTPController @Autowired constructor(val parser: ObjectMapper){

    @RequestMapping("/greeting")
    open fun greeting(@RequestParam(value = "name", required = false, defaultValue = "World")
                          name: String): String {
        return "Hello, $name!"
    }

    @PostConstruct
    open fun initBot() {
        val events = EventRegistry(FileInputStream("events.json"), jacksonObjectMapper())
        ApiContextInitializer.init()
        val botsApi = TelegramBotsApi()
        val bot = MarcoPaoloBot(System.getenv()["BOT_TOKEN"]!!)
        bot.register(StaImaGetNextEventCommand(events))
        botsApi.registerBot(bot)
    }
}