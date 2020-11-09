package br.com.dev.mygiftlist.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SwaggerConfig {

    @Bean
    fun customOpenAPI(@Value("\${application-description}") appDescription: String,
                      @Value("\${application-version}") appVersion: String): OpenAPI {
        return OpenAPI()
                .info(Info()
                        .title("sample application API")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms/")
                        .license(License().name("Apache 2.0").url("http://springdoc.org")))
    }
}
