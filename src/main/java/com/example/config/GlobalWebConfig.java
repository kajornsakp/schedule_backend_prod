package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.model.Account;
import com.example.repository.AccountRepository;

@Configuration
public class GlobalWebConfig extends GlobalAuthenticationConfigurerAdapter{
	@Autowired private AccountRepository repository;
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				System.out.println("authentication start: " + username);
				Account user = repository.findByUsername(username);
				if (user != null){
					return new User(user.getUsername(), user.getPassword(), true ,true,true,true, 
							AuthorityUtils.createAuthorityList(user.getRole()));
				} else {
					throw new UsernameNotFoundException("user not found in the system");
				}
			}
			
		};
	}

}
