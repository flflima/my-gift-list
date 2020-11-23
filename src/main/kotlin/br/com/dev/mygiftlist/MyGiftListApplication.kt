package br.com.dev.mygiftlist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyGiftListApplication

fun main(args: Array<String>) {
    runApplication<MyGiftListApplication>(*args)
}
