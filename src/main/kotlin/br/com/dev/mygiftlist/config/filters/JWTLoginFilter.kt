package br.com.dev.mygiftlist.config.filters

import br.com.dev.mygiftlist.dtos.UserDTO
import br.com.dev.mygiftlist.services.TokenAuthenticationService
import com.fasterxml.jackson.databind.json.JsonMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import java.util.Collections
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(
    url: String,
    authManager: AuthenticationManager,
    private val mapper: JsonMapper,
    private val tokenAuthenticationService: TokenAuthenticationService
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials: UserDTO = this.mapper.readValue(request.inputStream, UserDTO::class.java)
        return this.authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                credentials.username,
                credentials.password,
                Collections.emptyList()
            )
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
        auth: Authentication
    ) {
        this.tokenAuthenticationService.addAuthentication(response, auth.name)
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        this.tokenAuthenticationService.addError(response, failed)
    }
}
