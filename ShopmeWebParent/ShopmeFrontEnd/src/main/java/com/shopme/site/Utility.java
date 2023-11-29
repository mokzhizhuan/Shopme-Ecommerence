package com.shopme.site;

import jakarta.servlet.http.HttpServlet;

public class Utility {

	public static String getSiteURL(HttpServlet request) {
		// TODO Auto-generated method stub
		return null;
	}
	/*public static String getSiteURL(HttpServlet request) {
		String siteURL = request.getRequestURL().toString();
		
		return siteURL.replace(request.getServletPath(), "");
	}*/
	
	/*public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settings) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(settings.getHost());
		mailSender.setPort(settings.getPort());
		mailSender.setUsername(settings.getUsername());
		mailSender.setPassword(settings.getPassword());
		
		Properties mailProperties = new Properties();
		mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
		mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());
		
		mailSender.setJavaMailProperties(mailProperties);
		
		return mailSender;
	}*/
	
	/*public static String getEmailOfAuthenticatedCustomer(HttpServlet request) {
		Object principal = request.getUserPrincipal();
		if (principal == null) return null;
		
		String customerEmail = null;
		
		if (principal instanceof UsernamePasswordAuthenticationToken 
				|| principal instanceof RememberMeAuthenticationToken) {
			customerEmail = request.getUserPrincipal().getName();
		} else if (principal instanceof OAuth2AuthenticationToken) {
			//OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
			//CustomerOAuth2User oauth2User = (CustomerOAuth2User) oauth2Token.getPrincipal();
			customerEmail = oauth2User.getEmail();
		}
		
		return customerEmail;
	}*/	
	
}
