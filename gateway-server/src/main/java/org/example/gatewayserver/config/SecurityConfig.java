package org.example.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final ReactiveClientRegistrationRepository registrationRepository;

    public SecurityConfig(ReactiveClientRegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .oauth2Login(withDefaults())
                .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));

        return http.build();
    }

    @Bean
    public ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler successHandler = new OidcClientInitiatedServerLogoutSuccessHandler(registrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:8761/");
        return successHandler;
    }
}
