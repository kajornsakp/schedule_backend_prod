package com.example.security;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static java.util.Collections.emptyList;

import java.io.IOException;

public class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 864_000_000;
	static final String SECRET = "Beareater05";
	static final String TOK_PREFIX = "naianaia";
	static final String HEADER = "Authorization";
	
	static void addAuthentication(HttpServletResponse res, String username) throws IOException{
		System.out.println("adding authentication token");
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		res.addHeader(HEADER, TOK_PREFIX + " " + JWT);
		res.getWriter().write("{\"" + HEADER + "\" : \"" + TOK_PREFIX + JWT + "\"}");
		
	}
	
	static Authentication getAuthentication(HttpServletRequest request){
		System.out.println("getting authentication");
		// print check request
		
		Enumeration params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = (String)params.nextElement();
		 System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
		
		//done
		String token = request.getHeader(HEADER);
		if (token != null){
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOK_PREFIX,""))
					.getBody()
					.getSubject();
			if (user != null){
				System.out.println("user != null");
				return new UsernamePasswordAuthenticationToken(user, null, emptyList());
			}
			else
				return null;
		}
		return null;
	}
}
