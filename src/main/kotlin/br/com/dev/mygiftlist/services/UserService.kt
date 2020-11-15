package br.com.dev.mygiftlist.services

import br.com.dev.mygiftlist.dtos.UserDTO
import br.com.dev.mygiftlist.dtos.toUserDocument
import br.com.dev.mygiftlist.exceptions.UserAlreadyRegistredException
import br.com.dev.mygiftlist.models.UserDocument
import br.com.dev.mygiftlist.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
open class UserService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

//    fun findOne(username: String): UserDTO = this.userRepository.findByUsername(username).toUserDTO()

    override fun loadUserByUsername(username: String): UserDetails {
        val userDocument: UserDocument = this.userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User not found")

        val authorities = listOf(SimpleGrantedAuthority("user"))

        return User(userDocument.username, userDocument.password, authorities)
    }

    fun createUser(user: UserDTO) {
        if (this.userRepository.findByUsername(user.username) == null) {
            this.userRepository.save(user.toUserDocument().copy(password = this.passwordEncoder.encode(user.password)))
        } else {
            throw UserAlreadyRegistredException("Usuário já cadastrado!")
        }
    }
}