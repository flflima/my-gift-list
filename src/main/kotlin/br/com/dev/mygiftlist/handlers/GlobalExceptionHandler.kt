package br.com.dev.mygiftlist.handlers

import br.com.dev.mygiftlist.dtos.ErrorDTO
import br.com.dev.mygiftlist.dtos.MyResponseDTO
import br.com.dev.mygiftlist.exceptions.UserAlreadyRegisteredException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [UserAlreadyRegisteredException::class])
    fun defaultErrorHandler(
        req: HttpServletRequest,
        e: UserAlreadyRegisteredException
    ): ResponseEntity<MyResponseDTO<ErrorDTO>> =
        ResponseEntity(
            MyResponseDTO("fail", ErrorDTO(message = e.message ?: "An error occurred!")),
            HttpStatus.CONFLICT
        )

}
