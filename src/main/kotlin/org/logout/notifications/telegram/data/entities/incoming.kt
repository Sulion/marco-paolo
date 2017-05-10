package org.logout.notifications.telegram.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.logout.notifications.telegram.data.DefaultConstructor
import java.math.BigDecimal
import java.util.*

@DefaultConstructor
data class InfobipIncomingPackage(val results: List<InfobipIncomingMessage>,
                                  val messageCount: Int,
                                  val pendingMessageCount: Int)

@DefaultConstructor
data class InfobipIncomingMessage(val from: String,
                                  @JsonProperty("to") val toKey: String,
                                  val receivedAt: Date,
                                  val message: MessageBody,
                                  val price: Price)

@DefaultConstructor
data class Price(val pricePerMessage: BigDecimal,
                 val currency: Currency)
