package org.logout.notifications.telegram.infobipbot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.client.RestTemplate


@Configuration
@EnableScheduling
open class InfobipAPIConfiguration {
    @Value("\${telegram.infobip.baseUrl}")
    lateinit var baseUrl: String

    @Value("\${telegram.infobip.applicationKey}")
    lateinit var applicationKey: String

    @Value("\${telegram.infobip.ownerLogin}")
    lateinit var ownerLogin: String

    @Value("\${telegram.infobip.ownerPassword}")
    lateinit var ownerPassword: String

    @Bean open fun restTemplate(restTemplateBuilder: RestTemplateBuilder) =
            restTemplateBuilder
                    .basicAuthorization(ownerLogin, ownerPassword)
                    .build()


    @Bean open fun infobipTelegramService(restTemplate: RestTemplate) =
            InfobipTelegramService(baseUrl = baseUrl, applicationKey = applicationKey, template = restTemplate)

    @Bean
    open fun taskScheduler(): TaskScheduler {
        //org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
        return ThreadPoolTaskScheduler()
    }
}