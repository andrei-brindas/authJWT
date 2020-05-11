package ro.andrei.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.andrei.auth.security.JwtProvider;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

   @Autowired
   JwtProvider jwtProvider;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
           throws ServletException, IOException {
      Authentication authentication = null;
      String authToken = null;
      try {
         authToken = request.getHeader(jwtProvider.getTokenHeader()).replace(jwtProvider.getTokenPrefix()+" ", "");
         authentication = jwtProvider.getAuthentication(authToken);
         SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
      chain.doFilter(request, response);
   }
}

