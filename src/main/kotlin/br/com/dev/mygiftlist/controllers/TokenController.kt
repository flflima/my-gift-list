package br.com.dev.mygiftlist.controllers

import br.com.dev.mygiftlist.dtos.MyResponseDTO
import br.com.dev.mygiftlist.dtos.MyTokenDTO
import br.com.dev.mygiftlist.dtos.UserDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/tokens")
class TokenController {

    @RequestMapping(
        value = ["/generate"],
        method = [RequestMethod.POST],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    @Throws(AuthenticationException::class)
    fun register(@RequestBody user: UserDTO): ResponseEntity<MyResponseDTO<MyTokenDTO>> =
        ResponseEntity(MyResponseDTO("", MyTokenDTO("", LocalDateTime.now())), HttpStatus.OK)
    // FIXME: this is only for swagger configuration. Is there a better way to do this??
}
