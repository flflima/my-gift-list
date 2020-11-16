package br.com.dev.mygiftlist.services

import br.com.dev.mygiftlist.dtos.UserDTO
import br.com.dev.mygiftlist.exceptions.InvalidUserInputException
import br.com.dev.mygiftlist.exceptions.UserAlreadyRegisteredException
import br.com.dev.mygiftlist.models.UserDocument
import br.com.dev.mygiftlist.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {

    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `given a valid username should search for an user and if found must return an UserDetails`() {
        // arrange
        `when`(this.userRepository.findByUsername(anyString()))
            .thenReturn(UserDocument(ObjectId(), "user", "password"))

        // act
        val user = this.userService.loadUserByUsername("user")

        // assert
        assertThat(user).isNotNull
        assertThat(user.username).isEqualTo("user")
        assertThat(user.password).isEqualTo("password")
    }

    @Test
    fun `given a valid username should search for an user and if not found must throw an exception`() {
        // arrange
        `when`(this.userRepository.findByUsername(anyString())).thenReturn(null)

        // act
        val exception = assertThrows<UsernameNotFoundException> {
            this.userService.loadUserByUsername("user")
        }

        // assert
        assertThat(exception).hasMessage("User not found")
    }

    @Test
    fun `given an username should validate it and if it is empty must throw an exception`() {
        // arrange
        val emptyUserName = ""

        // act
        val exception = assertThrows<InvalidUserInputException> {
            this.userService.loadUserByUsername(emptyUserName)
        }

        // assert
        assertThat(exception).hasMessage("Username field is empty!")
    }

    @Test
    fun `given an user should verify if it exists and if it does not must save the new user`() {
        // arrange
        `when`(this.userRepository.findByUsername(anyString())).thenReturn(null)
        `when`(this.passwordEncoder.encode(anyString())).thenReturn("encrypted-password")

        // act
        this.userService.createUser(UserDTO("user", "password"))

        // assert
        verify(this.userRepository, times(1)).save(any(UserDocument::class.java))
    }

    @Test
    fun `given an user should verify if it exists and if it does must throw an exception`() {
        // arrange
        `when`(this.userRepository.findByUsername(anyString()))
            .thenReturn(UserDocument(ObjectId(), "user", "password"))

        // act
        val exception = assertThrows<UserAlreadyRegisteredException> {
            this.userService.createUser(UserDTO("user", "password"))
        }

        // assert
        assertThat(exception).hasMessage("User already registered!")
    }

    @Test
    fun `given an user if it has an invalid username must throw an exception`() {
        // arrange
        val emptyUserName = ""

        // act
        val exception = assertThrows<InvalidUserInputException> {
            this.userService.createUser(UserDTO(emptyUserName, "password"))
        }

        // assert
        assertThat(exception).hasMessage("Username field is empty!")
    }
}