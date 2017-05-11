package org.logout.notifications.telegram

import org.logout.notifications.telegram.data.entities.InfobipIncomingPackage
import org.logout.notifications.telegram.infobipbot.InfobipTelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*


@RestController
open class MainHTTPController @Autowired constructor(
        @Value("\${telegram.token}") private val botToken: String,
        private val bot: InfobipTelegramBot) {


    @RequestMapping("/greeting")
    open fun greeting(@RequestParam(value = "name", required = false, defaultValue = "World")
                      name: String): String {
        return "Hello, $name!"
    }

    @PostMapping("/accept/{botToken}")
    open fun acceptTelegramUpdate(@PathVariable("botToken") token: String,
                                  incomingPackage: InfobipIncomingPackage) {
        if (botToken != token)
            throw SecurityException("Unrecognized URL")
        bot.onMessages(incomingPackage)
    }

    @PostMapping("/notifyAll/{botToken}")
    open fun sendToEveryone(@PathVariable("botToken") token: String,
                            @RequestBody messageText: String) {
        if (botToken != token)
            throw SecurityException("Unrecognized URL")
        bot.sendToEveryone(messageText)
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