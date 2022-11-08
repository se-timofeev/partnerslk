package ru.planetnails.partnerslk.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.planetnails.partnerslk.logger.Logger;
import ru.planetnails.partnerslk.service.LoggerService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private static final String AUTHORIZATION = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    private final LoggerService loggerService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            final Claims claims = jwtTokenProvider.getAccessClaims(token);
            Authentication auth = jwtTokenProvider.getAuthentication(claims);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }
        try {
            Logger logger=new Logger();
            logger.setRemoteAddr(request.getRemoteAddr());
            logger.setRemotePort(request.getRemotePort());
            logger.setRequestURI(((HttpServletRequest) request).getRequestURI());

            loggerService.save(logger);
        } catch(Exception e) {
            log.info(e.toString());
        }

        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
