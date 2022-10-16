package com.auth2.oseidclient.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.FindUserByUsernameService;

@ExtendWith(MockitoExtension.class)
public class FindUserByEmailServiceTest {

	@Mock
	private OseidUserDetailsRepository mockOseidUserDetailsRepository;

	@InjectMocks
	private FindUserByUsernameService findUserByUsernameService;
	
	@Test
	public void givenNotRegisteredUser_whenFindUserCalled_thenDefaultNotRegisteredUserShouldBeReturned() {
		
		OseidUserDetails expectedUser = new OseidUserDetails("Not_Registered");
		expectedUser.setLocked(false);
		expectedUser.setEmail("Not_Registered");
		
		when(mockOseidUserDetailsRepository.findByUsername("max")).thenReturn(Optional.empty());
		
		OseidUserDetails foundUser = findUserByUsernameService.findUserByUsername("max");
		
		assertThat(foundUser).isEqualTo(expectedUser);		
		
	}
	
	@Test
	public void givenLaxARegisteredUser_whenFindUserCalled_thenLaxShouldBeReturned() {
		
		OseidUserDetails expectedUser = new OseidUserDetails("max");
		expectedUser.setLocked(false);
		
		when(mockOseidUserDetailsRepository.findByUsername("max")).thenReturn(Optional.of(expectedUser));
		
		OseidUserDetails foundUser = findUserByUsernameService.findUserByUsername("max");
		
		assertThat(foundUser).isEqualTo(expectedUser);		
		
	}
	
}
