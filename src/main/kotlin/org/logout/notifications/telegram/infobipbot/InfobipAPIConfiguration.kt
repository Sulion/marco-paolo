package org.logout.notifications.telegram.infobipbot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
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

}