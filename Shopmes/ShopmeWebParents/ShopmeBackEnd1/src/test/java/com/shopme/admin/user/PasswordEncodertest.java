package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodertest 
{
	@Test
	public void testEncodePass()
	{
		BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
		String rawpassword="fgew246800";
		String encodepass = passwordEncode.encode(rawpassword);
		
		System.out.println(encodepass);
		
		boolean matches = passwordEncode.matches(rawpassword, encodepass);
		
		assertThat(matches).isTrue();
	}
	
}
