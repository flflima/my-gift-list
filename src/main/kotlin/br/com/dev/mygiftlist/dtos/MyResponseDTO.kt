package br.com.dev.mygiftlist.dtos

data class MyResponseDTO<T>(val status: String, val data: T)