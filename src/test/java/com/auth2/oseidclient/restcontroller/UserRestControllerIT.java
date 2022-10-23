package com.auth2.oseidclient.restcontroller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.entity.UserId;
import com.auth2.oseidclient.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private UserService userService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Nested
	@DisplayName("postUser")
	class postUser{
		
		@Test
		public void performPostNewUserAndGetStatusCreated() throws Exception {
			
	/*		OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
			newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			newUser.setRoles("ROLE_USER");
			newUser.setEnabled(true);
			newUser.setLocked(false);
	*/		
			OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
			defaultNotRegistered.setEmail("Not_Registered");
			
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(defaultNotRegistered);
			when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			
			mockMvc
				.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"sir@sir.com\",\"username\":\"sir@sir.com\",\"fullname\":\"klm\",\"password\":\"sir1M&vb\",\"role\":\"USER\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
				
		}
		
		@Test
		public void performPostNewUserAndVerifySaveUserCalledOnce() throws Exception {
			
			OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
			newUser.setEmail("sir@sir.com");
			newUser.setPassword("$2y$10$wZr547wCn796Tdi4/J8nnOKA2d/eIZmkzChhTni4epuITcaP47NwK");
			newUser.setRoles("USER");
			newUser.setEnabled(true);
			newUser.setLocked(false);
			
			OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
			
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(defaultNotRegistered);
			when(passwordEncoder.encode("sir1M&vb")).thenReturn("$2y$10$wZr547wCn796Tdi4/J8nnOKA2d/eIZmkzChhTni4epuITcaP47NwK");
			
			mockMvc
				.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"sir@sir.com\",\"username\":\"sir@sir.com\",\"fullname\":\"klm\",\"password\":\"sir1M&vb\",\"role\":\"USER\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		
			verify(userService, times(1)).saveUserDetails(newUser);
			
		}
		
		@Test
		public void performPostWtihEmptyBodyAndGetStatusBadRequest() throws Exception {
			
			OseidUserDetails registeredUser = new OseidUserDetails("sir@sir.com");
			registeredUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			registeredUser.setRoles("ROLE_USER");
			registeredUser.setEnabled(true);
			registeredUser.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(registeredUser);
			when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
					
			mockMvc
				.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
				
		}
		
		@Test
		public void whenPerformPostWithExistingUser_thenItShouldReturnStatusOk() throws Exception {
			
			OseidUserDetails registeredUser = new OseidUserDetails("sir@sir.com");
			registeredUser.setPassword("$2y$10$wZr547wCn796Tdi4/J8nnOKA2d/eIZmkzChhTni4epuITcaP47NwK");
			registeredUser.setRoles("USER");
			registeredUser.setEnabled(true);
			registeredUser.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(registeredUser);
			when(passwordEncoder.encode("sir")).thenReturn("$2y$10$wZr547wCn796Tdi4/J8nnOKA2d/eIZmkzChhTni4epuITcaP47NwK");
		
			mockMvc
				.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"sir@sir.com\",\"username\":\"sir@sir.com\",\"fullname\":\"klm\",\"password\":\"sir1M&vb\",\"role\":\"USER\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				
		}
		
		@Test
		public void givenARequestBodyRquiredUsernameFieldNull_whenPostUserPerformed_thenItShouldReturnBadRequestStatus() throws Exception {
			
			OseidUserDetails registeredUser = new OseidUserDetails("sir@sir.com");
			registeredUser.setPassword("$2y$10$wZr547wCn796Tdi4/J8nnOKA2d/eIZmkzChhTni4epuITcaP47NwK");
			registeredUser.setRoles("USER");
			registeredUser.setEnabled(true);
			registeredUser.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(registeredUser);
			when(passwordEncoder.encode("sir")).thenReturn("$2y$10$wZr547wCn796Tdi4/J8nnOKA2d/eIZmkzChhTni4epuITcaP47NwK");
		
			mockMvc
				.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"\",\"username\":\"\",\"fullname\":\"klm\",\"password\":\"sir1M&vb\",\"role\":\"USER\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
				
		}
		
	}
	
	@Nested
	@DisplayName("putUser")
	class putUser{
		
		@Test
		public void givenRegisteredUser_whenPutPerformedWithOutChange_thenItShouldReturnStatusIsOk() throws Exception{
			
			OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
			newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			newUser.setRoles("ROLE_USER");
			newUser.setEnabled(true);
			newUser.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(newUser);
			when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
					
			mockMvc
				.perform(put("/user")
					.with(user("user")
						.password("pass")
						.roles("ADMIN"))
					.param("username", "sir@sir.com")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"username\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_USER\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				;
			
			
		}
		
		@Test
		public void givenRegisteredUser_whenPutPerformedWithChange_thenItShouldReturnStatusIsOk() throws Exception{
			
			OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
			newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			newUser.setRoles("ROLE_USER");
			newUser.setEnabled(true);
			newUser.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(newUser);
			when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			
			
			mockMvc
				.perform(put("/user")
					.with(user("user")
						.password("pass")
						.roles("ADMIN"))
					.param("username", "sir@sir.com")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"username\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_ADMIN\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				;
			
			
		}
		
		@Test
		public void givenNotRegisteredUser_whenPutPerformed_thenItShouldReturnStatusIsNotFound() throws Exception{
			
			OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
			newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			newUser.setRoles("ROLE_USER");
			newUser.setEnabled(true);
			newUser.setLocked(false);
			
			OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
			
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(defaultNotRegistered);
			when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			
			
			mockMvc
				.perform(put("/user")
					.with(user("user")
						.password("pass")
						.roles("ADMIN"))
					.param("username", "sir@sir.com")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"username\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_ADMIN\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				;
			
			
		}
		
		@Test
		public void givenAnEmptyRequestBody_whenPutMapping_thenBadRequestStatusShouldBeReturned() throws Exception{
			
			OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
			newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			newUser.setRoles("ROLE_USER");
			newUser.setEnabled(true);
			newUser.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(newUser);
			when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			
			
			mockMvc
				.perform(put("/user")
					.with(user("user")
						.password("pass")
						.roles("ADMIN"))
					.param("username", "sir@sir.com")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				;
			
			
		}
		
	}
	
	@Nested
	@DisplayName("getUserByUsername")
	class getUserByUsername{
		
		@Test
		public void givenMaxRegistered_whenGetWhithEmailMaxCalled_thenMaxDetailsShoulBeFound() throws Exception {
			
			OseidUserDetails user = new OseidUserDetails("max");
			user.setRoles("ROLE_ADMIN");
			user.setEnabled(true);
			user.setLocked(false);
			
			when(userService.findUserByUsername("max")).thenReturn(user);
			
			mockMvc
				.perform(get("/user").with(
						SecurityMockMvcRequestPostProcessors.user("max")
						.password("pass").roles("USER","ADMIN"))
					.param("username", "max"))
				.andExpect(status().isOk());
			
		}
		
		@Test
		public void givenNotRegisteredUser_whenGetWhithEmailMaxCalled_thenMaxDetailsShoulBeFound() throws Exception {
			
			OseidUserDetails user = new OseidUserDetails("N_A");
			user.setRoles("ROLE_ADMIN");
			user.setEnabled(true);
			user.setLocked(false);
			
			when(userService.findUserByUsername("max")).thenReturn(user);
			
			mockMvc
				.perform(get("/user").with(
						SecurityMockMvcRequestPostProcessors.user("max")
						.password("pass").roles("USER","ADMIN"))
					.param("username", "max"))
				.andExpect(status().isOk());
			
		}
		
	}
	
	@Nested
	@DisplayName("getUserById")
	class getUserById{
		
		@Test
		public void givenARegisteredUser_whenGetUserId_thenItShouldReturnStatusOk() throws Exception{
			
			UserId uid = new UserId(1);
			OseidUserDetails foundUser = new OseidUserDetails("lax");
			
			foundUser.setUserId(uid);;
			foundUser.setPassword("hi&1Jkkl");
			foundUser.setFullname("laxtexmex");
			foundUser.setRoles("ADMIN");
			
			when(userService.findUserByUserId(1)).thenReturn(foundUser);
			
			mockMvc
				.perform(get("/userid")
						.param("id", "1")
						.with(user("lax")
							.password("laxx")
							.roles("ADMIN")))
				.andExpect(status().isOk())
				;
			
		}
		
		@Test
		public void givenANotRegisteredUser_whenGetUserId_thenItShouldReturnStatusIsNotFound() throws Exception{
			
			UserId uid = new UserId(-1);
			OseidUserDetails notRegistered = new OseidUserDetails("Not_Registered");		
			notRegistered.setUserId(uid);;
			
			when(userService.findUserByUserId(1)).thenReturn(notRegistered);
			
			mockMvc
				.perform(get("/userid")
						.param("id", "1")
						.with(user("lax")
							.password("laxx")
							.roles("ADMIN")))
				.andExpect(status().isNotFound())
				;
			
		}
		
	}
	
	@Nested
	@DisplayName("deleteUser")
	class deleteUser{
		
		@Test
		public void givenMaxRegisteredUser_whenDeleteByEmailCalled_thenItShouldReturnStatusOk() throws Exception{
			
			OseidUserDetails max = new OseidUserDetails("sir@sir.com");
			max.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			max.setRoles("ROLE_USER");
			max.setEnabled(true);
			max.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(max);
			
			mockMvc
				.perform(delete("/user")
					.with(user("admin")
						.password("pass")
						.roles("ADMIN"))
					.param("username", "sir@sir.com"))
				.andExpect(status().isOk())
				;
			
		}
		
		@Test
		public void givenMaxRegisteredUser_whenDeleteByEmailCalled_thenDeleteUserMethodIShouldBeCalledOnceAndStatusIsOk() throws Exception{
			
			OseidUserDetails max = new OseidUserDetails("sir@sir.com");
			max.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
			max.setRoles("ROLE_USER");
			max.setEnabled(true);
			max.setLocked(false);
					
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(max);
			
			mockMvc
				.perform(delete("/user")
					.with(user("admin")
						.password("pass")
						.roles("ADMIN"))
					.param("username", "sir@sir.com"))
				.andExpect(status().isOk())
				;
			
			verify(userService, times(1)).deleteUserByUsername("sir@sir.com");
			
		}
		
		@Test
		public void givenNotRegisteredUser_whenDeleteUserByEmailCalled_thenItShouldReturnStatusNotFound() throws Exception{
			
			OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
			
			when(userService.findUserByUsername("sir@sir.com")).thenReturn(defaultNotRegistered);		
			
			mockMvc
				.perform(delete("/user")
					.with(user("max")
						.password("pass")
						.roles("ADMIN"))
					.param("username", "sir@sir.com"))
				.andExpect(status().isNotFound())
				;
		}
		
	}
	
}
