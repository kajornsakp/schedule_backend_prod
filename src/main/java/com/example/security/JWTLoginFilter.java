package com.example.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	public JWTLoginFilter(String url, AuthenticationManager authManager){
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		if (req.getHeader("Origin") != null){
			String origin = req.getHeader("Origin");
			res.addHeader("Access-Control-Allow-Origin", origin);
			res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			res.addHeader("Access-Control-Allow-Credentials",  "true");
			res.addHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
		}
		if (req.getMethod().equals("OPTIONS")){
			res.getWriter().println("KUY");
			res.getWriter().flush();

		}
		AccountCredentials creds = new ObjectMapper()
		.readValue(req.getInputStream(), AccountCredentials.class);
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						creds.getUsername(),
						creds.getPassword(),
						Collections.emptyList()
						)
				);
				
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
			if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
					response.setStatus(HttpServletResponse.SC_OK);
					
			TokenAuthenticationService.addAuthentication(response,  authResult.getName());
	}
	
	

}
