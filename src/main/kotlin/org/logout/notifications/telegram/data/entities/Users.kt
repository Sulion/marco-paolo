package org.logout.notifications.telegram.data.entities

import org.logout.notifications.telegram.data.DefaultConstructor

@DefaultConstructor
data class Users(val users: List<User>)