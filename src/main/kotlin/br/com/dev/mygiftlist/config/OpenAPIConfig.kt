package br.com.dev.mygiftlist.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class OpenAPIConfig {

    @Bean
    fun customOpenAPI(
        @Value("\${application-description}") appDescription: String,
        @Value("\${application-version}") appVersion: String
    ): OpenAPI {

        val securitySchemeName = "Bearer Authentication";

        return OpenAPI()
            .info(
                Info()
                    .title("My Gifts API")
                    .version(appVersion)
                    .description(appDescription)
            ).addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}
