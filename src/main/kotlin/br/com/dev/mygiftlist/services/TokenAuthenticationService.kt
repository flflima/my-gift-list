package br.com.dev.mygiftlist.services

import br.com.dev.mygiftlist.dtos.MyResponse
import br.com.dev.mygiftlist.dtos.MyToken
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.io.PrintWriter
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


object TokenAuthenticationService {
    //FIXME
    private const val EXPIRATION_TIME_IN_MINUTES: Long = 10
    private const val SECRET = "MySecret"
    private const val TOKEN_PREFIX = "Bearer"
    private const val HEADER_STRING = "Authorization"

    fun addAuthentication(response: HttpServletResponse, username: String?) {
        val mapper = JsonMapper.builder()
                .addModule(KotlinModule())
                .addModule(JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).build()

        val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        val expirationDate = LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES)
        val jwt: String = Jwts.builder()
                .setSubject(username)
                //FIXME
                .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact()

        response.contentType = "application/json"

        val myResponse = MyResponse("success", MyToken("$TOKEN_PREFIX $jwt", expirationDate))
        val out: PrintWriter? = response.writer
        out?.print(mapper.writeValueAsString(myResponse))
        out?.flush()
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            val user: String = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
                    .subject

            return UsernamePasswordAuthenticationToken(user, null, emptyList())
        }
        return null
    }
}