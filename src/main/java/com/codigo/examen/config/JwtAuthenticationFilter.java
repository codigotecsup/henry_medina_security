package com.codigo.examen.config;

import com.codigo.examen.service.JWTService;
import com.codigo.examen.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private  final UsuarioService usuarioService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String autHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        if(StringUtils.isEmpty(autHeader) || !StringUtils.startsWithIgnoreCase(autHeader, "Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt=autHeader.substring(7);
        userName=jwtService.extractUserName(jwt);
        // * Si el username no es nulo pero en el contexto de autenticación si es null
        if(Objects.nonNull(userName) && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(userName);
            if(jwtService.validateToken(jwt,userDetails)){
                // * Creamos un contexto
                SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // * Añadimos la autenticación a nuestro contexto creado
                securityContext.setAuthentication(authToken);
                // *Añadimos nuestro contesto al contexto general
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request,response);
    }
}
