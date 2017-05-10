package org.logout.notifications.telegram.data.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream

/**
 * Created by sulion on 10.05.17.
 */
class EventRegistry(stream: InputStream, parser: ObjectMapper) {
    val data = parser.readValue<List<Event>>(stream)

}