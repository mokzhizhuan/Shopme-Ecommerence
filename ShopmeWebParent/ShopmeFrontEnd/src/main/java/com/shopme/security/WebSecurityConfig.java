package com.shopme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.shopme.security.oauth.CustomerOAuth2UserService;
import com.shopme.security.oauth.OAuth2LoginSuccessHandler;



@Configuration
public class WebSecurityConfig {
	
	@Autowired 
	private CustomerOAuth2UserService oAuth2UserService;
	
	@Autowired 
	private OAuth2LoginSuccessHandler oauth2LoginHandler;
	
	@Autowired 
	private DatabaseLoginSuccessHandler databaseLoginHandler;
	
	@Bean
	public UserDetailsService userDetailsService() 
	{
		return new CustomerUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() 
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() 
	{
		return new PlainTextPasswordEncoder();
	}
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
		.requestMatchers("/account_details", "/update_account_details", "/orders/**",
				"/cart", "/address_book/**", "/checkout", "/place_order", "/reviews/**", 
				"/process_paypal_order", "/write_review/**", "/post_review").authenticated()
		.anyRequest().permitAll())
		.formLogin(login -> login.loginPage("/login")
			.usernameParameter("email")
			.successHandler(databaseLoginHandler)
			.permitAll())
		.oauth2Login(oauth -> oauth
			.loginPage("/login")
			.userInfoEndpoint(user -> user.userService(oAuth2UserService))
			.successHandler(oauth2LoginHandler))
		.logout(logout -> logout.permitAll())
		.rememberMe(remenber -> remenber.key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ"))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));	
		
      
        http.headers(head -> head.frameOptions(frame -> frame.sameOrigin()));
 
        return http.build();
    }
 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
	     
}
