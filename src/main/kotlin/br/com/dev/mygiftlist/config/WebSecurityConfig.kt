package br.com.dev.mygiftlist.config

import br.com.dev.mygiftlist.config.filters.JWTAuthenticationFilter
import br.com.dev.mygiftlist.config.filters.JWTLoginFilter
import br.com.dev.mygiftlist.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: UserService

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST, "/tokens/generate").permitAll()
            .antMatchers(HttpMethod.POST, "/users/register").permitAll()
            .anyRequest().authenticated()
            .and()
            // filtra requisições de login
            .addFilterBefore(
                JWTLoginFilter("/tokens/generate", authenticationManager()),
                UsernamePasswordAuthenticationFilter::class.java
            )
            // filtra outras requisições para verificar a presença do JWT no header
            .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(webSecurity: WebSecurity) {
        webSecurity.ignoring().antMatchers(
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html/**",
            "/swagger-ui/**",
            "/webjars/**"
        )
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(builder: AuthenticationManagerBuilder) {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }
}