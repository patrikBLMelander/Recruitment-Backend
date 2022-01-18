package com.recruitmentbackend.recruitmentbackend.config.security.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
public class RequestLoggingFilter implements Filter {

    public static final String CORRELATION_ID = "correlation_id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
        var start = Instant.now();

        log.info("New Request");
        filterChain.doFilter(request, response);
        log.info("Request finished in: {} ms", Duration.between(start, Instant.now()).toMillis());
        MDC.remove(CORRELATION_ID);
    }
}
