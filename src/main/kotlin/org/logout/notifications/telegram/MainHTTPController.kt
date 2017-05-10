package org.logout.notifications.telegram

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.logout.notifications.telegram.data.events.EventRegistry
import org.logout.notifications.telegram.infobipbot.InfobipTelegramService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.FileInputStream
import javax.annotation.PostConstruct


@RestController
open class MainHTTPController @Autowired constructor(val parser: ObjectMapper,
                                                     val infobipTelegramService: InfobipTelegramService) {

    @RequestMapping("/greeting")
    open fun greeting(@RequestParam(value = "name", required = false, defaultValue = "World")
                      name: String): String {
        return "Hello, $name!"
    }

    @PostMapping("/accept")
    open fun acceptTelegramUpdate(): String {

        return "OK"
    }

    @PostConstruct
    open fun initBot() {
        val events = EventRegistry(FileInputStream("events.json"), jacksonObjectMapper())
        println(infobipTelegramService.fetchUsers())
//        ApiContextInitializer.init()
//        val botsApi = TelegramBotsApi()
//        val bot = MarcoPaoloBot(System.getenv()["BOT_TOKEN"]!!)
//        bot.register(StaImaGetNextEventCommand(events))
//        botsApi.registerBot(bot)
    }
}