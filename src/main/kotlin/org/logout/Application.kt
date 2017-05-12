package org.logout

import org.logout.notifications.telegram.infobipbot.InfobipTelegramBot
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.scheduling.TaskScheduler
import org.springframework.web.client.RestTemplate
import javax.annotation.PostConstruct


@SpringBootApplication
@EnableAutoConfiguration
open class Application {
    @Autowired
    lateinit var bot: InfobipTelegramBot

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var taskExecutor: TaskScheduler

    @PostConstruct
    open fun pingMyself() {
        taskExecutor.scheduleWithFixedDelay(
                {
                    try {
                        val greeting = restTemplate.getForObject(
                                "https://marco-paolo.herokuapp.com/greeting?name=myself",
                                String::class.java)
                        log.info("I have received a greeting from myself: {}", greeting)
                    } catch (ex: Exception) {
                        log.info(ex.message, ex)
                    }
                },
                20 * 60 * 1000)
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(Application::class.java).run(*args)
        }

        val log = LoggerFactory.getLogger(Application::class.java)
    }
}

