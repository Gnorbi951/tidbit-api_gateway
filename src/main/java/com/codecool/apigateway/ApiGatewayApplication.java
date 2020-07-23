package com.codecool.apigateway;

import com.codecool.apigateway.entity.UserEntity;
import com.codecool.apigateway.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
@EnableEurekaClient
public class ApiGatewayApplication {

    @Autowired
    ZuulProperties zuulProperties;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build();
    }
    @Bean
    public PasswordEncoder getEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            zuulProperties.getRoutes().values().stream()
                    .forEach(route -> resources
                            .add(createResource(route.getServiceId(), route.getServiceId(), "version1")));
            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {

            repository.saveAndFlush(UserEntity.builder()
                    .name("Lajos")
                    .password(passwordEncoder.encode("password"))
                    .email("olajos@gmail.com")
                    .roles(Collections.singletonList("USER"))
                    .build()
            );

            repository.saveAndFlush(UserEntity.builder()
                    .name("admin")
                    .password(passwordEncoder.encode("password"))
                    .email("root@google.com")
                    .roles(Arrays.asList("USER", "ADMIN"))
                    .build()
            );
            repository.saveAndFlush(UserEntity.builder()
                    .name("Gábor")
                    .password(passwordEncoder.encode("password"))
                    .email("gaben@gmail.com")
                    .roles(Arrays.asList("USER", "ADMIN"))
                    .build()
            );
            repository.saveAndFlush(UserEntity.builder()
                    .name("Ádám")
                    .password(passwordEncoder.encode("password"))
                    .email("adi@gmail.com")
                    .roles(Arrays.asList("USER", "ADMIN"))
                    .build()
            );



        };
    }

}
