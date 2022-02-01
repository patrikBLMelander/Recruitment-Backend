package com.recruitmentbackend.recruitmentbackend.config.security;

import com.recruitmentbackend.recruitmentbackend.config.CorsConfig;
import com.recruitmentbackend.recruitmentbackend.config.security.filters.CustomAuthenticationFilter;
import com.recruitmentbackend.recruitmentbackend.config.security.filters.JWTAuthorizationFilter;
import com.recruitmentbackend.recruitmentbackend.config.security.filters.RequestLoggingFilter;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

import static com.recruitmentbackend.recruitmentbackend.controller.AppConstants.API_MAPPING.*;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTIssuer jwtIssuer;
    private final PasswordEncoder passwordEncoder;
    private final CandidateRepository candidateRepo;
    private final CorsFilter corsFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((username) -> {
            var candidate = candidateRepo.findByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found"));
            return User.builder()
                    .username(candidate.getEmail())
                    .password(candidate.getPassword())
                    .authorities(candidate.getRoleList().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                            .collect(Collectors.toSet()))
                    .build();
        }).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();
        final JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager(), jwtIssuer);
        final CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager(), jwtIssuer);


        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(BASE_API + CANDIDATES + "/**").hasRole(Role.RoleConstant.CANDIDATE.name())
                .antMatchers(BASE_API + ADMIN + "/**").hasRole(Role.RoleConstant.ADMIN.name())
                .antMatchers(BASE_API+ "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(requestLoggingFilter, CustomAuthenticationFilter.class)
                .addFilter(corsFilter)
                .addFilter(authenticationFilter)
                .addFilter(jwtAuthorizationFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
