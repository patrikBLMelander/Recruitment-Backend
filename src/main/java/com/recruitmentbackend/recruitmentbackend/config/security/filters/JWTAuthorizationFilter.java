package com.recruitmentbackend.recruitmentbackend.config.security.filters;

import com.recruitmentbackend.recruitmentbackend.config.security.JWTIssuer;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.recruitmentbackend.recruitmentbackend.controller.AppConstants.API_MAPPING.*;


/**
 * Handles JWT
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTIssuer jwtIssuer;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTIssuer jwtIssuer) {
        super(authenticationManager);
        this.jwtIssuer = jwtIssuer;
    }

    /**
     * Gets the header and authorizes the token inside the header
     *
     * @param request  from request by user
     * @param response to send to user
     * @param chain    a list of filters
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        final String jwt_token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt_token == null || !jwt_token.startsWith("Bearer") || checkServletPath(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(jwt_token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * Loggs when authentications is unsuccessful
     *
     * @param request  from request by user
     * @param response to send to user
     * @param failed   authentication exception
     */
    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("onUnsuccessfulAuthentication {}", failed.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, failed.getMessage());
    }

    /**
     * Validates the JWT-token
     *
     * @param jwt_token token from the request with users name and password
     * @return a UsernamePasswordAuthenticationToken object
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String jwt_token) {
        log.info("getAuthentication: {}", jwt_token);

        Claims claims = jwtIssuer.validate(jwt_token.substring("Bearer ".length()));

        log.info("in get auth, {}", claims.get("authorities"));

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, getAuthorities(claims));
    }

    /**
     * Gets the authorities and splits them into separate roles
     *
     * @param claims a object with authenticated userinformation
     * @return a collection with SimpleGrantedAuthority
     */
    public Collection<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String authorities = (String) claims.get("authorities");
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private boolean checkServletPath(String servletPath) {
        return servletPath.equals(BASE_API + PUBLIC + CANDIDATES) ||
                servletPath.equals(BASE_API + PUBLIC + CREATE);
    }
}