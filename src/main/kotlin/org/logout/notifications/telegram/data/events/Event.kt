package org.logout.notifications.telegram.data.events

import java.util.*

data class Event(val startDate: Date,
                 val eventName: String,
                 val performerName: String,
                 val trackName: String) {
    constructor() : this(Date(), "", "", "")
}

