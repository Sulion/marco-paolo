package org.logout.notifications.telegram.bot.processor

import org.logout.notifications.telegram.data.events.Event
import org.logout.notifications.telegram.data.events.EventRegistry
import java.text.SimpleDateFormat
import java.util.*

class StaImaNextEventProcessor(private val eventRegistry: EventRegistry) {
    fun onMessage(arguments: Array<String>?): String =
            if (arguments == null || arguments.isEmpty()) {
                allNextEvents()
            } else {
                "I'm not that smart yet"
            }

    private fun simpleNextEvent(): String {
        val next = eventRegistry.findNextEventAfter(Date())
        return if (next != null) {
            renderSingleEvent(next)
        } else {
            "You don't seem to have anything planned. Enjoy your spare time!"
        }
    }

    private fun renderSingleEvent(event: Event) =
            "Your next event is ${event.eventName} by " +
                    "${event.performerName} at ${render(event.startDate)} on " +
                    "${event.trackName} track. Get ready!"

    private fun renderMultipleEvents(events: List<Event>): String {
        val msg = StringBuilder().append("Your next events are: \n")
        events.forEachIndexed { index, (startDate, eventName, performerName, trackName) ->
            msg.append("$index. $eventName by " +
                    "$performerName at ${render(startDate)} on $trackName track\n")
        }

        return msg.append("Choose wisely!").toString()
    }


    private fun allNextEvents(): String {
        val nextEvents = eventRegistry.findAllEventsRightAfter(Date())
        return when (nextEvents.size) {
            0 -> "You don't seem to have anything planned. Enjoy your spare time!"
            1 -> renderSingleEvent(nextEvents[0])
            else -> renderMultipleEvents(nextEvents)
        }
    }

    private fun render(date: Date): String =
            SimpleDateFormat("MMM dd, HH:mm").apply {
                timeZone = TimeZone.getTimeZone("Europe/Zagreb")
            }.format(date)

}