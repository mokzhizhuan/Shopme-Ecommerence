package com.shopme.admin.setting.country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.common.entity.Country;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryRestControllerTests {

	@Autowired
	MockMvc mockmvctests;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired 
	CountryRepository repo;
	
	@Test
	@WithMockUser(username = "gamerdark44@outlook.com" , password = "kk126523", roles="ADMIN")
	public void testListCountries() throws Exception
	{
		String url = "/countries/list";
		MvcResult result = mockmvctests.perform(get(url))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		
		Country[] countries = objectMapper.readValue(jsonResponse, Country[].class);
		for(Country country : countries)
		{
			System.out.print(country);
		}
		
		assertThat(countries).hasSizeGreaterThan(0);
	}
	
	@Test
	@WithMockUser(username = "gamerdark44@outlook.com" , password = "kk126523", roles="ADMIN")
	public void testCreateCountry() throws JsonProcessingException, Exception 
	{
		String url = "/countries/save";
		String countryName = "Germany";
		String countryCode = "DE";
		Country country = new Country(countryName, countryCode);
		
		MvcResult result = mockmvctests.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(country))
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		String response = result.getResponse().getContentAsString();
		Integer countryId = Integer.parseInt(response);
		
		Optional<Country> findById = repo.findById(countryId);
		assertThat(findById.isPresent());
		
		Country savedCountry = findById.get();
		
		assertThat(savedCountry.getName()).isEqualTo(countryName);
	}
	
	@Test
	@WithMockUser(username = "gamerdark44@outlook.com" , password = "kk126523", roles="ADMIN")
	public void testUpdateCountry() throws JsonProcessingException, Exception 
	{
		String url = "/countries/save";
		
		Integer countryId = 7;
		String countryName = "Singapore";
		String countryCode = "SG";
		Country country = new Country(countryId, countryName, countryCode);
		
		mockmvctests.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(country))
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(countryId)));
				
		Optional<Country> findById = repo.findById(countryId);
		assertThat(findById.isPresent());
		
		Country savedCountry = findById.get();
		
		assertThat(savedCountry.getName()).isEqualTo(countryName);
	}
	
	@Test
	@WithMockUser(username = "nam@codejava.net", password = "something", roles = "ADMIN")
	public void testDeleteCountry() throws Exception 
	{
		Integer countryId = 7;
		String url = "/countries/delete/" + countryId;
		mockmvctests.perform(get(url)).andExpect(status().isOk());
		
		Optional<Country> findById = repo.findById(countryId);
		
		assertThat(findById).isNotPresent();
	}
}
