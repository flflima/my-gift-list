package br.com.dev.mygiftlist.config

import br.com.dev.mygiftlist.config.filters.JWTAuthenticationFilter
import br.com.dev.mygiftlist.config.filters.JWTLoginFilter
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                // filtra requisições de login
                .addFilterBefore(JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter::class.java)
                // filtra outras requisições para verificar a presença do JWT no header
                .addFilterBefore(JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        // cria uma conta default
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}password")
                .roles("ADMIN")
    }
}