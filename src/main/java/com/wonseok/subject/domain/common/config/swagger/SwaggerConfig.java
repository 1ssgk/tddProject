package com.wonseok.subject.domain.common.config.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
  info = @Info(
          title = "API 명세서",
          description = "설명",
          version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
  
  @Bean
  public GroupedOpenApi openApi(){
    String[] paths = {"/ws/**"};

    return GroupedOpenApi.builder()
            .group("")
            .pathsToMatch(paths)
            .build();
  }
}
