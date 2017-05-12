package org.logout.notifications.telegram.bot.processor

import org.logout.notifications.telegram.data.events.Event
import org.logout.notifications.telegram.data.events.EventRegistry
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StaImaNextEventProcessor(private val eventRegistry: EventRegistry) : Processor {

    companion object {
        val log = LoggerFactory.getLogger(StaImaNextEventProcessor::class.java)
    }

    private val IDUNNO_ANSWER = listOf("I'm not that smart yet")

    override fun onMessage(arguments: Array<String>?): List<String> =
            if (arguments == null || arguments.isEmpty()) {
                allNextEvents(this::renderSingleEvent, this::renderMultipleEvents)
            } else {
                if (arguments.size == 1) {
                    when (arguments[0]) {
                        "today" -> renderEventsForToday()
                        "-v" -> allNextEvents(this::renderSingleEventWithDetails, this::renderMultipleEventsWithDetails)
                        "now" -> allCurrentEvents(this::renderSingleCurrentEventWithDetails, this::renderMultipleCurrentEventsWithDetails)
                        else -> IDUNNO_ANSWER
                    }
                } else
                    IDUNNO_ANSWER
            }

    private fun allCurrentEvents(singleRenderer: (Event) -> List<String>,
                                 multipleRenderer: (List<Event>) -> List<String>): List<String> {
        val events = eventRegistry.findLastStartedEvents(Date())
        val resultLines = ArrayList<String>()
        if (events.isNotEmpty()) {
            val startDate = events[0].startDate
            val minutesSince = (System.currentTimeMillis() - startDate.time) / 1000 / 60
            if (minutesSince < 15)
                resultLines.add("Hurry up! This is what I found has started in the last 15 minutes:\n")
            if (minutesSince in 15..30)
                resultLines.add("I think you'll still be able to make it:\n")
            if (minutesSince in 30..60)
                resultLines.add("This is what has been happening recently, but I don't think it still on:\n")
            if (minutesSince > 60) {
                resultLines.add("No events have started in the last hour. Shouldn't you be sleeping?")
                return resultLines
            }
        }
        resultLines.addAll(when (events.size) {
            0 -> renderNoEvents(Date())
            1 -> singleRenderer(events[0])
            else -> multipleRenderer(events)
        })
        return batchLines(resultLines)
    }



    private val MAX_MSG_LENGTH = 1000

    private fun renderEventsForToday(): List<String> {
        val nextEvents = eventRegistry.findEventsForToday(Date())
        return when (nextEvents.size) {
            0 -> renderNoEvents(Date())
            else -> {
                val msg = ArrayList<String>().apply { add("Your schedule for the rest of the day: \n") }
                msg.addAll(nextEvents.mapIndexed {
                    index, (startDate, eventName, performerName, trackName) ->
                    "$index. ${render2(startDate)}: $eventName by " +
                            "$performerName on $trackName track\n"
                })
                return batchLines(msg)
            }
        }
    }

    private fun renderSingleEvent(event: Event) =
            listOf("Your next event is ${event.eventName} by " +
                    "${event.performerName} at ${render(event.startDate)} on " +
                    "${event.trackName} track. Get ready!")

    private fun renderSingleEventWithDetails(event: Event) =
            listOf("Your next event is ${event.eventName} by " +
                    "${event.performerName} at ${render(event.startDate)} on " +
                    "${event.trackName} track. Abstract: " +
                    "${renderDescription(event.eventDescription, 100)}\n Get ready!")

    private fun renderSingleCurrentEventWithDetails(event: Event) =
            listOf("${event.eventName} by " +
                    "${event.performerName} on " +
                    "${event.trackName} track has started at ${render(event.startDate)}. Abstract: " +
                    "${renderDescription(event.eventDescription, 100)}\n")

    private fun renderDescription(str: String?, offset: Int) =
            str?.substring(0, Math.min(MAX_MSG_LENGTH - offset, str.length)) ?: ""


    private fun renderMultipleEvents(events: List<Event>): List<String> {
        val msg = ArrayList<String>().apply { add("Your next events are: \n") }
        msg.addAll(events.mapIndexed {
            index, (startDate, eventName, performerName, trackName) ->
            "$index. $eventName by $performerName at ${render(startDate)} on $trackName track\n"
        })
        msg.add("Choose wisely!")
        return batchLines(msg)
    }

    private fun renderMultipleEventsWithDetails(events: List<Event>): List<String> {
        val msg = ArrayList<String>().apply { add("Your next events are: \n") }
        msg.addAll(events.mapIndexed {
            index, (startDate, eventName, performerName, trackName, eventDescription) ->
            "$index. $eventName by $performerName at ${render(startDate)} on $trackName track. " +
                    "Abstract: ${renderDescription(eventDescription, 150)} \n"
        })
        msg.add("Choose wisely!")
        return batchLines(msg)
    }

    private fun renderMultipleCurrentEventsWithDetails(events: List<Event>): List<String> {
        val msg = ArrayList<String>()
        msg.addAll(events.mapIndexed {
            index, (startDate, eventName, performerName, trackName, eventDescription) ->
            "$index. $eventName by $performerName has started at ${render(startDate)} on $trackName track. " +
                    "Abstract: ${renderDescription(eventDescription, 150)} \n"
        })
        msg.add("Choose wisely!")
        return msg
    }


    private fun allNextEvents(singleRenderer: (Event) -> List<String>,
                              multipleRenderer: (List<Event>) -> List<String>): List<String> {
        val nextEvents = eventRegistry.findAllEventsRightAfter(Date())
        return when (nextEvents.size) {
            0 -> renderNoEvents(Date())
            1 -> singleRenderer(nextEvents[0])
            else -> multipleRenderer(nextEvents)
        }
    }

    private fun renderNoEvents(date: Date) =
            if (date.after(eventRegistry.getLastEventTime())) {
                listOf("Dev Days have ended. See you in 2018!")
            } else {
                listOf("You don't seem to have anything planned. Enjoy your spare time!")
            }


    private fun render(date: Date): String =
            SimpleDateFormat("MMM dd, HH:mm").apply {
                timeZone = TimeZone.getTimeZone("Europe/Zagreb")
            }.format(date)

    private fun render2(date: Date): String =
            SimpleDateFormat("HH:mm").apply {
                timeZone = TimeZone.getTimeZone("Europe/Zagreb")
            }.format(date)

    internal fun batchLines(lines: List<String>): List<String> {
        var temp = StringBuilder()
        val results = ArrayList<String>()
        for (line in lines) {
            if (temp.length + line.length > MAX_MSG_LENGTH) {
                results.add(temp.toString())
                temp = StringBuilder()
            }
            temp.append(line)
        }
        results.add(temp.toString())
        return results
    }
}