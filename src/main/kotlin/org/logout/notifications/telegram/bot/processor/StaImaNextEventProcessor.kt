package org.logout.notifications.telegram.bot.processor

import org.logout.notifications.telegram.data.events.Event
import org.logout.notifications.telegram.data.events.EventRegistry
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*

class StaImaNextEventProcessor(private val eventRegistry: EventRegistry) {

    companion object {
        val log = LoggerFactory.getLogger(StaImaNextEventProcessor::class.java)
    }

    fun onMessage(arguments: Array<String>?): String =
            if (arguments == null || arguments.isEmpty()) {
                allNextEvents()
            } else {
                if (arguments.size == 1 && "today" in arguments) {
                    renderEventsForToday()
                } else
                    "I'm not that smart yet"
            }

    private val MAX_MSG_LENGTH = 1000

    private fun renderEventsForToday(): String {
        val nextEvents = eventRegistry.findEventsForToday(Date())
        return when (nextEvents.size) {
            0 -> renderNoEvents(Date())
            else -> {
                val msg = StringBuilder().append("Your schedule for the rest of the day: \n")
                nextEvents.forEachIndexed {
                    index, (startDate, eventName, performerName, trackName) ->
                    run {
                        val line = "$index. ${render2(startDate)}: $eventName by " +
                                "$performerName on $trackName track\n"
                        if (msg.length + line.length < MAX_MSG_LENGTH)
                            msg.append(line)
                    }
                }
                msg.toString().also { log.info("This will be sent: {}", it) }
            }
        }
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
            0 -> renderNoEvents(Date())
            1 -> renderSingleEvent(nextEvents[0])
            else -> renderMultipleEvents(nextEvents)
        }
    }

    private fun renderNoEvents(date: Date) =
            if (date.after(eventRegistry.getLastEventTime())) {
                "Dev Days have ended. See you in 2018!"
            } else {
                "You don't seem to have anything planned. Enjoy your spare time!"
            }


    private fun render(date: Date): String =
            SimpleDateFormat("MMM dd, HH:mm").apply {
                timeZone = TimeZone.getTimeZone("Europe/Zagreb")
            }.format(date)

    private fun render2(date: Date): String =
            SimpleDateFormat("HH:mm").apply {
                timeZone = TimeZone.getTimeZone("Europe/Zagreb")
            }.format(date)

}