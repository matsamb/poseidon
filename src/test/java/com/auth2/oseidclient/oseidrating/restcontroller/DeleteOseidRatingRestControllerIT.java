package com.auth2.oseidclient.oseidrating.restcontroller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.repository.OseidRatingRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteOseidRatingRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OseidRatingRepository oseidRatingRepository;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(delete("/rating"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void test() throws Exception{
		
		mockMvc
			.perform(delete("/rating")
					.param("id", "1")
					.with(user("nex")
						.password("mex")
						.roles("ADMIN")))
			.andExpect(status().isOk())
			;
		
	}
	
}
