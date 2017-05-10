package org.logout.notifications.telegram.data.entities

import org.logout.notifications.telegram.data.DefaultConstructor

@DefaultConstructor
data class User(val key: String,
                val firstName: String?,
                val lastName: String?,
                val username: String?)