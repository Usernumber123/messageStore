package com.efimov.messageStore.security;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) {
        String tokenHeaderValue = httpServletRequest.getHeader(AUTHORIZATION);
        String username ;
        String jwtToken ;
        if (tokenHeaderValue != null && tokenHeaderValue.startsWith("Bearer ")) {
            jwtToken = tokenHeaderValue.substring(7);
            username = jwtTokenService.getUsernameFromToken(jwtToken);
            val context = SecurityContextHolder.getContext();
           if (username != null && context.getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                val usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                context.setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}