package com.ykeshtdar.StartP9Monolothic.filter;

import com.ykeshtdar.StartP9Monolothic.service.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.filter.*;
import org.springframework.web.util.*;

import java.io.*;
import java.util.*;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final RestTemplate restTemplate;
    private final JwtService jwtService;
    private final CustomUserDetailService userDetailService;




    public JwtAuthFilter(RestTemplate restTemplate, JwtService jwtService, CustomUserDetailService userDetailService) {
        this.restTemplate = restTemplate;
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtService.getUsername(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailService.loadUserByUsername(userName);

            if (jwtService.validateToken(token)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}

