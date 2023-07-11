package main.config;

import main.security.jwt.JwtConfigure;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";
    private static final String ADMIN_ENDPOINT = "/tasks/{id}";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Autowired
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers(LOGIN_ENDPOINT, REGISTRATION_ENDPOINT).permitAll()
                .requestMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtConfigure(jwtTokenProvider));

        return http.build();
    }
}

