package kafoor.quizzes.quizzes_service.utils.filters;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.utils.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String accessToken = null;
        String username = null;

        if(authHeader != null
                && !authHeader.substring(7).trim().equals("null")
                && authHeader.substring(7).trim() != null
                && authHeader.startsWith("Bearer ")){
            accessToken = authHeader.substring(7);
            if (!jwtUtils.validateToken(accessToken)){
                System.out.println("ВАЛИД");
                username = jwtUtils.getUsernameFromToken(accessToken);
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            if(!jwtUtils.validateToken(accessToken)){
                Claims claims = jwtUtils.getClaimsFromToken(accessToken);
                List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles");
                Collection<? extends GrantedAuthority> authorities = roles.stream()
                        .map(roleMap -> new SimpleGrantedAuthority(roleMap.get("authority")))
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}