package com.component.security.config;


import com.component.security.config.filter.JwtTokenFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author jinx
 */
public class SecurityConfig {


    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        JwtTokenFilter tokenFilter = new JwtTokenFilter();

        DefaultSecurityFilterChain defaultSecurityFilterChain = http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/docs/**").permitAll()
                                .anyRequest().authenticated()
                )
//                .userDetailsService(userDetailsService)
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

        return defaultSecurityFilterChain;
    }

}
