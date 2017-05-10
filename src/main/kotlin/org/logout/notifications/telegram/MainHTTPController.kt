package org.logout.notifications.telegram

import org.logout.notifications.telegram.bot.MarcoPaoloBot
import org.logout.notifications.telegram.bot.commands.StaImaGetNextEventCommand
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import javax.annotation.PostConstruct


@RestController
open class MainHTTPController {

    @RequestMapping("/greeting")
    open fun greeting(@RequestParam(value = "name", required = false, defaultValue = "World")
                          name: String): String {
        return "Hello, $name!"
    }

    @PostConstruct
    open fun initBot() {
        ApiContextInitializer.init()
        val botsApi = TelegramBotsApi()
        val bot = MarcoPaoloBot(System.getenv()["BOT_TOKEN"]!!)
        bot.register(StaImaGetNextEventCommand())
        botsApi.registerBot(bot)
    }
}