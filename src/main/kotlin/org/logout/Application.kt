package org.logout

import org.logout.notifications.telegram.infobipbot.InfobipTelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication
@EnableAutoConfiguration
open class Application {
    @Autowired
    lateinit var bot: InfobipTelegramBot

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(Application::class.java).run(*args)
        }
    }
}

