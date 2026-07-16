package com.droid.chatik

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatikApplication

fun main(args: Array<String>) {
    runApplication<ChatikApplication>(*args)
}
