package br.com.dev.mygiftlist.repositories

import br.com.dev.mygiftlist.models.UserDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<UserDocument, ObjectId> {
    fun findByUsername(username: String): UserDocument?
}