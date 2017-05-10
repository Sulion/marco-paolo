package org.logout

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication
@EnableAutoConfiguration
open class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            println("Hello, World")
            SpringApplicationBuilder(Application::class.java).run(*args)
        }
    }
}

