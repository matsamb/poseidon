package com.auth2.oseidclient.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.service.OseidLeruService;

@SpringBootTest
@AutoConfigureMockMvc
public class OseidLeruRestControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OseidLeruService oseidLeruService;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				// .defaultRequest(get("/bid"))
				.apply(springSecurity()).build();

	}

	@Nested
	@DisplayName("postLeru")
	class postLeru {

		@Test
		public void givenANewLeru_whenPostLeruIsCalled_thenStatusCreatedShouldBeReturned() throws Exception {

			OseidLeru leruSaved = new OseidLeru();
			leruSaved.setName("mate");

			OseidLeru leruReturned = new OseidLeru();
			leruReturned.setId(1);
			leruReturned.setName("mate");

			when(oseidLeruService.saveOseidLeru(leruReturned)).thenReturn(leruReturned.getId());

			mockMvc.perform(post("/leru").with(user("mate").password("taste").roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content(
							"{\"id\":\"1\",\"name\":\"mate\",\"description\":\"zelo\",\"json\":\"melo\",\"template\":\"hello\", \"template\":\"hello\", \"template\":\"hello\"}")
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		}

		@Test
		public void givenANewLeruMissingNameConstraint_whenPostLeruIsCalled_thenStatusCreatedShouldBeReturned()
				throws Exception {

			mockMvc.perform(post("/leru").with(user("mate").password("taste").roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"id\":\"\",\"name\":\"\",\"description\":\"yeyo\"}").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());

		}

		@Test
		public void givenAnEmptyRequestBody_whenPostLeruIsCalled_thenStatusBadRequestShouldBeReturned()
				throws Exception {

			mockMvc.perform(post("/leru").with(user("mate").password("taste").roles("ADMIN")))
					.andExpect(status().isBadRequest());

		}

	}

	@Nested
	@DisplayName("deleteLeru")
	class deleteLeru {

		@Test
		public void test() throws Exception {
			
			OseidLeru registered = new OseidLeru();
			registered.setId(1);
			
			when(oseidLeruService.deleteOseidLeru(1)).thenReturn(true);
			
			mockMvc.perform(delete("/leru")
						.param("id", "1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"\",\"name\":\"\",\"description\":\"yeyo\"}").accept(MediaType.APPLICATION_JSON)					
						.with(user("nwl")
							.password("nyjfk")
							.roles("Admin")))
						.andExpect(status().isOk())
			;
			
		}
		
		@Test
		public void registered() throws Exception {
			
			OseidLeru registered = new OseidLeru();
			registered.setId(1);
			
			when(oseidLeruService.deleteOseidLeru(1)).thenReturn(false);
									
			mockMvc.perform(delete("/leru")
						.param("id", "1")
						.with(user("nwl")
							.password("nyjfk")
							.roles("Admin")))
						.andExpect(status().isNotFound())
			;
			
		}

	}

	@Nested
	@DisplayName("getLeru")
	class getLeru {

		@Test
		public void givenARegisteredRule_whenGetOseidRule_thenItShouldReturnStatusIsOk() throws Exception{
			
			OseidLeru tex = new OseidLeru();
			tex.setId(1);
			
			when(oseidLeruService.findOseidLeruById(1)).thenReturn(tex);
			
			mockMvc.perform(get("/leru")
						.param("id", "1")	
						.with(user("laex")
							.password("fleax")
							.roles("ADMIN")))
					.andExpect(status().isOk())
					;
			
		}
		
		@Test
		public void givenANotRegisteredRule_whenGetOseidRule_thenItShouldReturnStatusIsNotFound() throws Exception{
			
			OseidLeru notRegistered = new OseidLeru();
			notRegistered.setId(-1);
			
			when(oseidLeruService.findOseidLeruById(1)).thenReturn(notRegistered);
			
			mockMvc.perform(get("/leru")
						.param("id", "1")	
						.with(user("laex")
							.password("fleax")
							.roles("ADMIN")))
					.andExpect(status().isNotFound())
					;
			
		}
		
	}

	@Nested
	@DisplayName("putLeru")
	class putLeru {

		@Test
		public void givenARegisteredRule_whenPutOseidRule_thenItShouldReturnStatusIsOk() throws Exception{
			
			OseidLeru max = new OseidLeru();
			max.setId(1);
			
			when(oseidLeruService.findOseidLeruById(1)).thenReturn(max);
			
			mockMvc
				.perform(put("/leru") 
					.param("id", "1")	
					.with(user("flex")
						.password("prex")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"id\":\"1\",\"name\":\"mateson\",\"description\":\"yayo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				;
			
		}
		
		@Test
		public void givenAnEmptyRequestBody_whenPutRule_thenItShouldReturnStatusIsBadRequest() throws Exception{
			
			OseidLeru max = new OseidLeru();
			max.setId(1);
			
			when(oseidLeruService.findOseidLeruById(1)).thenReturn(max);
			
			mockMvc
				.perform(put("/leru") 
					.param("id", "1")	
					.with(user("flex")
						.password("prex")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				;
			
		}
		
		@Test
		public void givenANotRegisteredOseidRule_whenPutOseidRule_thenItShouldReturnStatusIsNotFound() throws Exception{
			
			OseidLeru notRegistred = new OseidLeru();
			notRegistred.setId(-1);
			
			when(oseidLeruService.findOseidLeruById(1)).thenReturn(notRegistred);
			
			mockMvc
				.perform(put("/leru") 
					.param("id", "1")	
					.with(user("flex")
						.password("prex")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"id\":\"1\",\"name\":\"mateson\",\"description\":\"yayo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				;
			
		}
		
	}

}
