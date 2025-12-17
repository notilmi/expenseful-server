package org.ilmi.expensefulserver.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.ilmi.expensefulserver.security.helper.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ExpensefulUserDetailsService userDetailService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        // If token still null
        if (token == null) {
            String header = request.getHeader(HEADER_STRING);
            if (header != null && header.startsWith(TOKEN_PREFIX)) {
                token = header.replace(TOKEN_PREFIX, "").trim();
            }
        }

        if (token != null && jwtHelper.validateToken(token)) {
            String tokenSubject = jwtHelper.getSubjectFromToken(token);

            if (tokenSubject == null) {
                logger.warn("[IdentityAccess] Token subject is null, ignoring the token");
                filterChain.doFilter(request, response);
                return;
            }

            ExpensefulUserDetails userDetails = (ExpensefulUserDetails) userDetailService.loadByUserId(tokenSubject);

            UsernamePasswordAuthenticationToken authentication = jwtHelper.getAuthenticationToken(
                    SecurityContextHolder.getContext().getAuthentication(),
                    userDetails
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            logger.info("[IdentityAccess] User authenticated: {}, setting security context", userDetails.getId());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
