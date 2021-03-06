package br.com.dev.mygiftlist.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class UserDocument(
    @Id val id: ObjectId?,
    @Indexed(name = "unique_username_uk", unique = true) val username: String,
    val password: String
)
