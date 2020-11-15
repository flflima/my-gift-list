package br.com.dev.mygiftlist.controllers

import br.com.dev.mygiftlist.dtos.UserDTO
import br.com.dev.mygiftlist.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST], consumes = ["application/json"], produces = ["application/json"])
    @Throws(AuthenticationException::class)
    fun register(@RequestBody user: UserDTO): ResponseEntity<Any> {
        this.userService.createUser(user)
        return ResponseEntity(HttpStatus.CREATED)
    }

}