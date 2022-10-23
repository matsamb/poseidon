package com.auth2.oseidclient.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Incubating;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.entity.UserId;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private OseidUserDetailsRepository oseidUserDetailsRepository;

	@InjectMocks
	private UserService userService;

	@Test
	public void givenAUser_whenSaveCalled_thenRepositorySaveShouldBeCalledOnce() {
		OseidUserDetails userToSave = new OseidUserDetails();
		userService.saveUserDetails(userToSave);
		verify(oseidUserDetailsRepository, times(1)).save(userToSave);
	}

	@Nested
	@DisplayName("findUserByUserName")
	class findUserByUserName {

		@Test
		public void givenNotRegisteredUser_whenFindUserCalled_thenDefaultNotRegisteredUserShouldBeReturned() {
			
			OseidUserDetails expectedUser = new OseidUserDetails("Not_Registered");
			expectedUser.setLocked(false);
			expectedUser.setEmail("Not_Registered");
			
			when(oseidUserDetailsRepository.findByUsername("max")).thenReturn(Optional.empty());
			
			OseidUserDetails foundUser = userService.findUserByUsername("max");
			
			assertThat(foundUser).isEqualTo(expectedUser);		
			
		}
		
		@Test
		public void givenLaxARegisteredUser_whenFindUserCalled_thenLaxShouldBeReturned() {
			
			OseidUserDetails expectedUser = new OseidUserDetails("max");
			expectedUser.setLocked(false);
			
			when(oseidUserDetailsRepository.findByUsername("max")).thenReturn(Optional.of(expectedUser));
			
			OseidUserDetails foundUser = userService.findUserByUsername("max");
			
			assertThat(foundUser).isEqualTo(expectedUser);		
			
		}
		
	}

	@Nested
	@DisplayName("findUserByIdService")
	class findUserByIdService {

		@Test
		public void givenARegisteredUser_whenFindUserByIdCalled_thenTheUserShouldBeReturned() {
			
			UserId userId = new UserId(1);
			OseidUserDetails user = new OseidUserDetails();
			user.setEmail("mymail");
			user.setUserId(userId);
			
			List<OseidUserDetails> userList = new ArrayList<>();
			userList.add(user);
			
			when(oseidUserDetailsRepository.findAll()).thenReturn(userList);
			
			OseidUserDetails result = userService.findUserByUserId(1);
			
			assertThat(result).isEqualTo(user);
			
			
		}
		
		@Test
		public void givenNotRegisteredUser_whenFindUserByIdCalled_thenDefaultNotRegisteredUserShouldBeReturned() {
			
			UserId userId = new UserId(-1);
			OseidUserDetails user = new OseidUserDetails();
			user.setUsername("Not_Registered");
			user.setUserId(userId);
			
			List<OseidUserDetails> userList = new ArrayList<>();
			
			when(oseidUserDetailsRepository.findAll()).thenReturn(userList);
			
			OseidUserDetails result = userService.findUserByUserId(1);
			
			assertThat(result).isEqualTo(user);
			
			
		}
		
	}

	@Nested
	@DisplayName("postUser")
	class d {

	}

	@Nested
	@DisplayName("postUser")
	class e {

	}
}