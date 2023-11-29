package com.shopme.security.oauth;


import org.springframework.stereotype.Service;

@Service
public class CustomerOAuth2UserService extends DefaultOAuth2UserService {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String clientName = userRequest.getClientRegistration().getClientName();
		OAuth2User user = super.loadUser(userRequest);
		
		return new CustomerOAuth2User(user, clientName);
	}

}
