package br.com.dev.mygiftlist.dtos

import java.time.LocalDateTime

data class MyToken(val token: String, val expiration: LocalDateTime)