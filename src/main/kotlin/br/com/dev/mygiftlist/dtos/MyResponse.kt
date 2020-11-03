package br.com.dev.mygiftlist.dtos

data class MyResponse<T>(val status: String, val data: T)