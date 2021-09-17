package com.efimov.messageStore.security;

import com.efimov.messageStore.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    private CacheManager cacheManager;
    @Value("${variable.orc.url}")
    protected String ORCHESTRATOR_URL;
    public static final String AUTHORIZATION = "Authorization";
    public static final String CHAT_NAME = "chatName";
    public static final String USER_LOGIN = "userLogin";
    private final RestTemplate restTemplate;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) {
        String tokenHeaderValue = httpServletRequest.getHeader(AUTHORIZATION);
        String username;
        String jwtToken;
        if (tokenHeaderValue != null && tokenHeaderValue.startsWith("Bearer ")) {
            jwtToken = tokenHeaderValue.substring(7);
            username = jwtTokenService.getUsernameFromToken(jwtToken);
            val context = SecurityContextHolder.getContext();



                if (username != null ) {
                 if (context.getAuthentication() != null) {
                     UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                     if (!principal.getUsername().equals(username)) {
                         auth(tokenHeaderValue, username, context);
                     }
                 }
                 else {
                    auth(tokenHeaderValue, username, context);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void auth(String tokenHeaderValue, String username, SecurityContext context) throws JSONException {
        if (verificationByRest(username, tokenHeaderValue)) {
            if (cacheManager.getCache("Users").get(username)==null) {
                User user = new User();
                user.setLogin(username);
                user.setRole("USER");
                cacheManager.getCache("Users").put(username,user);
            }
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

    protected boolean verificationByRest(String username,String token) throws JSONException {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(ORCHESTRATOR_URL)
                .queryParam(CHAT_NAME, "all")
                .queryParam(USER_LOGIN, username);
        return sendRequestToOrchestrator(uriBuilder.toUriString(),token);
    }

    protected boolean sendRequestToOrchestrator(String url,String token) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        headers.set(AUTHORIZATION,  token);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return response.getStatusCode().equals(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

}