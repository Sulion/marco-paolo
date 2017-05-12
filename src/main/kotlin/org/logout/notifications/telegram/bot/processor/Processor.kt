package org.logout.notifications.telegram.bot.processor

/**
 * Created by sulion on 11.05.17.
 */
interface Processor {
    fun onMessage(arguments: Array<String>?): List<String>
}