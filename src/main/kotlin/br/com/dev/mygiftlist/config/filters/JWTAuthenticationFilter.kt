package br.com.dev.mygiftlist.config.filters

import br.com.dev.mygiftlist.services.TokenAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JWTAuthenticationFilter : GenericFilterBean() {

    @Autowired
    private lateinit var tokenAuthenticationService: TokenAuthenticationService

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val authentication: Authentication? =
            this.tokenAuthenticationService.getAuthentication((request as HttpServletRequest))
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}