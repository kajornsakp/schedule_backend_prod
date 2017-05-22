package com.example.security;

import java.io.IOException;
import java.util.Collections;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.config.MongoConfig;
import com.example.model.Account;
import com.example.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Criteria;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	
	
	public JWTLoginFilter(String url, AuthenticationManager authManager){
		super(new AntPathRequestMatcher(url, "POST"));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		res.addHeader("Access-Control-Allow-Origin", "*");
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
			//UserDetails authUser =
					 //(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			UserDetails authUser = (UserDetails)authResult.getPrincipal();
			
			
			TokenAuthenticationService.addAuthentication(response,  authUser);
	}
	
	

}
