package br.com.dev.mygiftlist.config.filters

import br.com.dev.mygiftlist.services.TokenAuthenticationService.getAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JWTAuthenticationFilter : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val authentication: Authentication? = getAuthentication((request as HttpServletRequest))
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}