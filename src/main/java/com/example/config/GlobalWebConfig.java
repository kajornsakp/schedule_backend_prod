package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.DataHandler.DataHandler;
import com.example.model.Account;

@Configuration
public class GlobalWebConfig extends GlobalAuthenticationConfigurerAdapter{
	
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
				Account user = DataHandler.findByUser(username);
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
