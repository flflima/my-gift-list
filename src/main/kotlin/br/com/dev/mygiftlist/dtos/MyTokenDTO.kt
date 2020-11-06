package br.com.dev.mygiftlist.dtos

import java.time.LocalDateTime

data class MyTokenDTO(val token: String, val expiration: LocalDateTime)