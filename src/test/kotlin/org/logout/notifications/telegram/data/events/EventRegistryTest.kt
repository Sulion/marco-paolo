package org.logout.notifications.telegram.data.events

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

/**
 * Created by sulion on 10.05.17.
 */
class EventRegistryTest {
    lateinit var registry: EventRegistry
    @Before
    fun setup() {
        registry = EventRegistry(javaClass.getResourceAsStream("testevents.json"),
                jacksonObjectMapper())
    }

    @Test
    fun test_findAllEventsRightAfter_OnlyOne() {
        val calendar = Calendar.getInstance()
        val date = calendar.apply { set(2017, Calendar.MAY, 8) }.time
        val result = registry.findAllEventsRightAfter(date)
        assertEquals(1, result.size)
        assertEquals("Overview of development and divisions intro",
                result[0].eventName)
    }

    @Test
    fun test_findAllEventsRightAfter_MoreThanOne() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val date = calendar.apply { set(2017, Calendar.MAY, 11, 10, 45, 0) }.time
        val result = registry.findAllEventsRightAfter(date)
        assertEquals(4, result.size)
        assertEquals("Openstack", result[0].eventName)
        assertEquals("Anomaly detection and monitoring", result[1].eventName)
        assertEquals("Spring Statemachine", result[2].eventName)
        assertEquals("Demystifying IPCore", result[3].eventName)
    }

    @Test
    fun test_findAllEventsRightAfter_Nothing() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val date = calendar.apply { set(2018, Calendar.MAY, 11, 10, 45, 0) }.time
        val result = registry.findAllEventsRightAfter(date)
        assertEquals(0, result.size)
    }

    @Test
    fun test_findAllEventsForTheDay_Some() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val date = calendar.apply {
            set(2017, Calendar.MAY, 11, 10, 20, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val result = registry.findEventsForToday(date)
        assertEquals(6, result.size)
    }

    @Test
    fun test_findCurrentEvent() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val date = calendar.apply {
            set(2017, Calendar.MAY, 11, 10, 55, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val result = registry.findLastStartedEvents(date)
        print(result.joinToString(separator = "\n") + "\n")
        val interval = date.time - result[0].startDate.time
        val minutesSince = interval / 1000 / 60
        println(minutesSince)
    }

}