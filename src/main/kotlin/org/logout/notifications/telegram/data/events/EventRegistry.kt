package org.logout.notifications.telegram.data.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream
import java.util.*


class EventRegistry(stream: InputStream, parser: ObjectMapper) {
    private val data = parser.readValue<List<Event>>(stream)
            .sortedBy { it.startDate }
    private val timezone = TimeZone.getTimeZone("Europe/Zagreb")

    fun findNextEventAfter(date: Date) =
            data.find { it.startDate.after(date) }

    fun findAllEventsRightAfter(date: Date): List<Event> {
        val firstEvent = data.find { it.startDate >= date } ?: return emptyList()
        return data.filter { it.startDate == firstEvent.startDate }
    }

    fun getLastEventTime() =
            data.last().startDate

    fun findEventsForToday(date: Date): List<Event> {
        val endDate = Calendar.getInstance(timezone).apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.time
        return data.filter { it.startDate.time in date.time..endDate.time }
    }

}