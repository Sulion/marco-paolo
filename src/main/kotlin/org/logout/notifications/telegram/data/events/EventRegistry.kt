package org.logout.notifications.telegram.data.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream
import java.util.*


class EventRegistry(stream: InputStream, parser: ObjectMapper) {
    private val data = parser.readValue<List<Event>>(stream)
            .sortedBy { it.startDate }

    fun findNextEventAfter(date: Date) =
            data.find { it.startDate.after(date) }

    fun findAllEventsRightAfter(date: Date): List<Event> {
        val firstEvent = data.find { it.startDate.after(date) } ?: return emptyList()
        return data.filter { it.startDate == firstEvent.startDate }
    }

}