package uk.ac.ebi.pride.solr.indexes.pride.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class SimpleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String url = request.getRequestURL().toString();
        if (!url.contains("/actuator") && !url.contains("/api-docs") && !url.contains("/swagger-ui")) {
            String msg = "RequestURL: " + request.getServletPath();
            String appName = request.getHeader("app");
            if (appName != null) {
                msg = "[RequesterAPP: " + appName + "] " + msg;
            }
            log.info(msg);
        }

        filterChain.doFilter(request, response);
    }
}