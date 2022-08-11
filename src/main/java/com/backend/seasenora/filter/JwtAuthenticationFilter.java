package com.backend.seasenora.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.backend.seasenora.exceptions.JwtTokenMissingException;
import com.backend.seasenora.services.CustomerAuthenticationServices;
import com.backend.seasenora.util.JwtUtil;
import com.backend.seasenora.model.ErrorResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
            .getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerAuthenticationServices customerAuthenticationServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authrorization");
        if (header == null || header.startsWith("HTTP_TOKEN")) {
            log.error("No JWT Token Found in the Request sent by customer");
            throw new JwtTokenMissingException("No Jwt Token Found in the request");

        }
        String token = header.substring("HTTP_TOKEN".length() + 1);
        jwtUtil.validateToken(token);
        String username = jwtUtil.getUserName(token);
        UserDetails userDetails = customerAuthenticationServices.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}
