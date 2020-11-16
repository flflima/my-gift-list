package br.com.dev.mygiftlist.dtos

import br.com.dev.mygiftlist.models.UserDocument

data class UserDTO(val username: String, val password: String)

fun UserDTO.toUserDocument(): UserDocument = UserDocument(null, this.username, this.password)