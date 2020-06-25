package com.example.demo.common.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// @OpenAPIDefinition(
//         info = @Info(
//                 title = "Demo API",
//                 version = "1.0",
//                 description = "Demo API Documentation v1.0"
//         )
// )
// @SecurityScheme(
//         name = PASSWORD_GRANT,
//         type = SecuritySchemeType.OAUTH2,
//         flows = @OAuthFlows(password = @OAuthFlow(
//                 tokenUrl = GET_TOKENS_URL,
//                 refreshUrl = REFRESH_TOKENS_URL,
//                 scopes = @OAuthScope(name = DEFAULT_SCOPE, description = "Default scope")
//         )),
//         description = "Username: jsmith@example.com | jdoe@example.com\nPassword: 123456\nclient: client, secret: secret"
// )
@Configuration
public class OpenApiConfig {

    public static final String LANG_EN = "en";
    public static final String LANG_RU = "ru";
    public static final String LANG_UK = "uk";
    public static final String PASSWORD_GRANT_TYPE = "Password grant type";
    public static final String GET_TOKENS_URL = "/oauth/token";
    public static final String REFRESH_TOKENS_URL = "/oauth/token";
    public static final String DEFAULT_SCOPE = "*";

    public OpenApiConfig() {
        SpringDocUtils springDocConfig = SpringDocUtils.getConfig();
        // springDocConfig.replaceWithClass(
        //         org.springframework.data.domain.Pageable.class,
        //         org.springdoc.core.converters.models.Pageable.class
        // );
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo API")
                        .version("1.0")
                        .description("Demo API Documentation v1.0\n\n" +
                                "**Authorization**\n\n\t" +
                                "- username: jsmith@example.com | jdoe@example.com\n\n\t" +
                                "- password: 123456\n\n\t" +
                                "- client: client\n\n\t" +
                                "- secret: secret")
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local server"),
                        new Server().url("http://api.develop.com").description("Develop server")
                ))
                .components(new Components()
                        .addSecuritySchemes(
                                PASSWORD_GRANT_TYPE,
                                new SecurityScheme()
                                        .scheme(PASSWORD_GRANT_TYPE)
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .password(new OAuthFlow()
                                                        .tokenUrl(GET_TOKENS_URL)
                                                        .refreshUrl(REFRESH_TOKENS_URL)
                                                        .scopes(new Scopes().addString(DEFAULT_SCOPE, "Default scope"))
                                                )
                                        )
                        )
                );
    }
}
