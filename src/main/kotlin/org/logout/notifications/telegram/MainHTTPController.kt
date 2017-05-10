package org.logout.notifications.telegram

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
open class MainHTTPController {

    @RequestMapping("/greeting")
    open fun greeting(@RequestParam(value = "name", required = false, defaultValue = "World")
                      name: String): String {
        return "Hello, $name!"
    }

    @PostMapping("/accept")
    open fun acceptTelegramUpdate(): String {

        return "OK"
    }

    //    @PostConstruct
    open fun initBot() {
//        ApiContextInitializer.init()
//        val botsApi = TelegramBotsApi()
//        val bot = MarcoPaoloBot(System.getenv()["BOT_TOKEN"]!!)
//        bot.register(StaImaGetNextEventCommand(events))
//        botsApi.registerBot(bot)
    }
}