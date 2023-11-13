package com.shopme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService UserDetailsService()
	{
		return new ShopmeUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder PasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.authorizeRequests().anyRequest()
		 	/*.requestMatchers("/users/**").hasAuthority("Admin").authenticated()
		 	.and().formLogin().loginPage("/login").usernameParameter("email")*/
		 	.permitAll();
		 //.and().logout().permitAll();
		 return http.build();
	 }
    
    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    	 return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }*/
	     
}
