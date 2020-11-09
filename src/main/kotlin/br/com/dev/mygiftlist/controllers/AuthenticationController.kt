package br.com.dev.mygiftlist.controllers

import br.com.dev.mygiftlist.dtos.UserDTO
import br.com.dev.mygiftlist.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/token")
class AuthenticationController {
//
//    @Autowired
//    private lateinit var authenticationManager: AuthenticationManager
//
//    @Autowired
//    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userService: UserService


    @RequestMapping(value = ["/generate-token"], method = [RequestMethod.POST], consumes = ["application/json"], produces = ["application/json"])
    @Throws(AuthenticationException::class)
    fun register(@RequestBody loginUser: UserDTO): ResponseEntity<*>? {
//        val authentication: Authentication = authenticationManager.authenticate(
//                UsernamePasswordAuthenticationToken(
//                        loginUser.username,
//                        loginUser.password
//                )
//        )
//
//        SecurityContextHolder.getContext().authentication = authentication
//
//        val user: UserDTO = this.userService.findOne(loginUser.username)
//        val token = this.jwtTokenUtil.generateToken(user)
//        return ResponseEntity.ok<Any>(MyTokenDTO(token))
        return null
    }
}
