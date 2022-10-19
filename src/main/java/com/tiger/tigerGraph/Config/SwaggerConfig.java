package com.tiger.tigerGraph.Config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.*;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("TigerGraph POC")
                        .description("This API provides Basic Operation with tigergraph")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Core Enterprise Services(CES)")
                                .email("coreenterpriseservices@Corp.sysco.com")
                                .url("https://sysco.com"))
                        .extensions(getExtensions()));

    }

    public Map<String, Object> getExtensions() {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("x-audience", Arrays.asList("sysco-internal", "external-partner"));
        extensions.put("x-updated-date", "2021-04-05");
        extensions.put("x-api-type", "enterprise-service");
        extensions.put("x-api-id", "bt-ces-product-information-api");
        return extensions;
    }


}