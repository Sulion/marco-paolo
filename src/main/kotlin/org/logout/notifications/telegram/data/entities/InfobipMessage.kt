package org.logout.notifications.telegram.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.logout.notifications.telegram.data.DefaultConstructor

@DefaultConstructor
data class InfobipMessage(val from: String,
                          @JsonProperty("to") val toKey: String,
                          val message: MessageBody)
@DefaultConstructor
data class MessageBody(val type: String,
                       val text: String)