package com.shopme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig {

	@Bean
	public UserDetailsService UserDetailsService()
	{
		return new ShopmeUserDetailsService();
	}
	
	/*@Bean
	public PasswordEncoder PasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}*/
	
	@Bean
	public PasswordEncoder nopasswordEncoder() {
	    return PlainTextPasswordEncoder.getInstance();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     
	    authProvider.setUserDetailsService(UserDetailsService());
	    authProvider.setPasswordEncoder(nopasswordEncoder());
	 
	    return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


		http.authenticationProvider(authenticationProvider());
		
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/login").permitAll()
        		.requestMatchers("/users/**", "/setting/**").hasAuthority("Admin")
        		.requestMatchers("/categories/**", "/brands/**", "/product/**").hasAnyAuthority("Admin", "Editor")
        		.requestMatchers("/product/edit/**", "/product/save", "/product/check_unique")
        		.hasAnyAuthority("Admin", "Editor", "Salesperson")
        		.requestMatchers("/product/", "/product/detail/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login")
                    .usernameParameter("email")
                    .permitAll())
                .rememberMe(remenber -> remenber.key("AbcdEfghIjklmNopQrsTuvXyz_0123456789"))
                .logout(logout -> logout.permitAll());
        
        http.headers(head -> head.frameOptions(frame -> frame.sameOrigin()));
        //http.authorizeRequests().anyRequest().permitAll();
 
        return http.build();
    }
 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
	     
}
