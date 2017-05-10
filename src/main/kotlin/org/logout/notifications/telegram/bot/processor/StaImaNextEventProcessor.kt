package org.logout.notifications.telegram.bot.processor

import org.logout.notifications.telegram.data.events.EventRegistry
import java.util.*

class StaImaNextEventProcessor(private val eventRegistry: EventRegistry) {
    fun onMessage(arguments: Array<String>?): String =
            when (arguments) {
                null -> simpleNextEvent()
                else -> "I'm not that smart yet"
            }

    private fun simpleNextEvent(): String {
        val next = eventRegistry.findNextEventAfter(Date())
        return if (next != null) {
            "Your next event is ${next.eventName} by " +
                    "${next.performerName} at ${next.startDate} on " +
                    "${next.trackName} track. Get ready!"
        } else {
            "You don't seem to have anything planned. Enjoy your spare time!"
        }
    }
}