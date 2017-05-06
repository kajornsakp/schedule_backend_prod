package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.JWTAuthenticationFilter;
import com.example.security.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private GlobalWebConfig globalProvider;
	
	private static final String ADMIN = "ADMIN";
	public void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeRequests()
			.antMatchers("/").permitAll()
			
			//.antMatchers(HttpMethod.POST,"/auth/login").permitAll()
			//.antMatchers("/auth/*").permitAll()
			//.antMatchers("/scheduleAct/*").permitAll()
			//.anyRequest().authenticated()
			.and()
			//.addFilterBefore(new JWTLoginFilter("/auth/login", authenticationManager()),
			//UsernamePasswordAuthenticationFilter.class)
			//.addFilterBefore(new JWTAuthenticationFilter(),
			//UsernamePasswordAuthenticationFilter.class)
			.csrf().disable();
	}
	
	
	
	

}