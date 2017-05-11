package org.logout.notifications.telegram.bot.processor


class HelpProcessor : Processor {
    override fun onMessage(arguments: Array<String>?): String =
            "/staima, /whatsup and a couple of secret aliases — the closest next event in near future\n" +
                    "/staima today — as much of today's schedule which will fit in 1000 characters. " +
                    "This is going to be changed soon to send the whole day's schedule.\n" +
                    "Anything else is just gets repeated back at you. Maybe. Or bot could suddenly acquire sentience and look back at you."
}

class StartProcessor : Processor {
    override fun onMessage(arguments: Array<String>?): String =
            "Welcome to Infobip DevDays 2017. Send /staima to find out what are the " +
                    "nearest events or /help for more information."

}