package com.oraclegui.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oraclegui.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		if(requestHeader != null && requestHeader.startsWith("Bearer ")) {
			jwtToken = requestHeader.substring(7);
		
			try {
				username = jwtTokenUtils.getUsernameFromToken(jwtToken);
			} catch(IllegalArgumentException ex) {
				System.out.println("Unable to get Jwt");
			} catch (ExpiredJwtException ex) {
				System.out.println("JWT expired");
			}
		}
		else {
			System.out.println("Jwt Token does not begin with Bearer String");
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			Long userId = this.jwtTokenUtils.getIdFromJwt(jwtToken);
			
			UserDetails userDetails = this.userDetailsService.loadUserById(userId);
			
			if(jwtTokenUtils.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
							= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
}
