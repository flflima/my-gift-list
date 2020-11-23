package br.com.dev.mygiftlist.services

import br.com.dev.mygiftlist.dtos.ErrorDTO
import br.com.dev.mygiftlist.dtos.MyResponseDTO
import br.com.dev.mygiftlist.dtos.MyTokenDTO
import com.fasterxml.jackson.databind.json.JsonMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import java.io.PrintWriter
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
open class TokenAuthenticationService {

    companion object {
        private const val EXPIRATION_TIME_IN_MINUTES: Long = 10
        private const val SECRET =
            "F)J@NcRfUjXn2r5u8x/A%D*G-KaPdSgVkYp3s6v9y\$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r5u8x/A?D(G+KbPdSgVkYp3s6v9y\$B&E)H@McQfThWmZq4t7"
        private const val TOKEN_PREFIX = "Bearer"
        private const val HEADER_STRING = "Authorization"
    }

    @Autowired
    private lateinit var mapper: JsonMapper

    fun addAuthentication(response: HttpServletResponse, username: String) {

        val expirationDate = LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES)
        val jwt = generateToken(username, expirationDate)

        response.contentType = "application/json"

        val myResponse = MyResponseDTO("success", MyTokenDTO("$TOKEN_PREFIX $jwt", expirationDate))
        val out: PrintWriter = response.writer
        out.print(this.mapper.writeValueAsString(myResponse))
        out.flush()
    }

    fun generateToken(
        username: String,
        expirationDate: LocalDateTime = LocalDateTime.now().plusMinutes(Companion.EXPIRATION_TIME_IN_MINUTES)
    ) = Jwts.builder()
        .setSubject(username)
        .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
        .compact()


    fun addError(response: HttpServletResponse, failed: AuthenticationException) {
        response.contentType = "application/json"
        response.status = HttpStatus.UNAUTHORIZED.value()

        val myResponse = MyResponseDTO("fail", ErrorDTO(failed.message ?: "Some error occurred"))
        val out: PrintWriter = response.writer
        out.print(this.mapper.writeValueAsString(myResponse))
        out.flush()
    }

    fun getAuthentication(request: HttpServletRequest): Authentication = UsernamePasswordAuthenticationToken(
        Jwts.parserBuilder()
            .setSigningKey(SECRET)
            .build()
            .parseClaimsJws((request.getHeader(HEADER_STRING) ?: "").replace(TOKEN_PREFIX, ""))
            .body
            .subject, null, emptyList()
    )
}
