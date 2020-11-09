package br.com.dev.mygiftlist.services

import br.com.dev.mygiftlist.dtos.UserDTO
import br.com.dev.mygiftlist.models.toUserDTO
import br.com.dev.mygiftlist.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
open class UserService: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

//    fun findOne(username: String): UserDTO = this.userRepository.findByUsername(username).toUserDTO()

    override fun loadUserByUsername(username: String): UserDetails {
        val user: br.com.dev.mygiftlist.models.User = this.userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User not found")

        val authorities = listOf<SimpleGrantedAuthority>(SimpleGrantedAuthority("user"))

        return User(user.username, user.password, authorities)
    }
}