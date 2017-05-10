package org.logout.notifications.telegram.infobipbot

import org.logout.notifications.telegram.data.entities.InfobipIncomingPackage
import org.logout.notifications.telegram.data.entities.InfobipMessage
import org.logout.notifications.telegram.data.entities.MessageBody
import org.logout.notifications.telegram.data.entities.Users
import org.springframework.web.client.RestTemplate

class InfobipTelegramService(val baseUrl: String,
                             val applicationKey: String,
                             val template: RestTemplate) {

    private val GET_USERS_URL = "$baseUrl/telegram/1/applications/$applicationKey/users"
    private val POST_MESSAGE_URL = "$baseUrl/telegram/1/single"
    private val GET_INCOMING_MESSAGES_URL = "$baseUrl/telegram/1/inbox/reports"


    fun fetchUsers(): Users =
            template.getForObject(GET_USERS_URL, Users::class.java)

    fun sendSingleMessage(msgText: String, address: String) {
        val result = template.postForEntity(POST_MESSAGE_URL,
                InfobipMessage(applicationKey, address,
                        MessageBody("TEXT", msgText)),
                Any::class.java)
        println(result)
    }

    fun receiveAllmessages() =
            template.getForObject(GET_INCOMING_MESSAGES_URL,
                    InfobipIncomingPackage::class.java)

}