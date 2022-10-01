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
import com.auth2.oseidclient.service.user.FindUserByEmailService;

@ExtendWith(MockitoExtension.class)
public class FindUserByEmailServiceTest {

	@Mock
	private OseidUserDetailsRepository mockOseidUserDetailsRepository;

	@InjectMocks
	private FindUserByEmailService findUserByEmailService;
	
	@Test
	public void givenNotRegisteredUser_whenFindUserCalled_thenDefaultNotRegisteredUserShouldBeReturned() {
		
		OseidUserDetails expectedUser = new OseidUserDetails("Not_Registered");
		expectedUser.setLocked(false);
		
		when(mockOseidUserDetailsRepository.findByEmail("max")).thenReturn(Optional.empty());
		
		OseidUserDetails foundUser = findUserByEmailService.findUserByEmail("max");
		
		assertThat(foundUser).isEqualTo(expectedUser);		
		
	}
	
	@Test
	public void givenLaxARegisteredUser_whenFindUserCalled_thenLaxShouldBeReturned() {
		
		OseidUserDetails expectedUser = new OseidUserDetails("max");
		expectedUser.setLocked(false);
		
		when(mockOseidUserDetailsRepository.findByEmail("max")).thenReturn(Optional.of(expectedUser));
		
		OseidUserDetails foundUser = findUserByEmailService.findUserByEmail("max");
		
		assertThat(foundUser).isEqualTo(expectedUser);		
		
	}
	
}
