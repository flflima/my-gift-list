package br.com.dev.mygiftlist.models

import br.com.dev.mygiftlist.dtos.UserDTO
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(@Id val id: ObjectId, @Indexed(unique = true) val username: String, val password: String)

fun User.toUserDTO(): UserDTO {
    return UserDTO(this.username, this.password)
}