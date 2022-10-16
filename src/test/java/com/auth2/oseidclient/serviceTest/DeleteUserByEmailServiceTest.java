package com.auth2.oseidclient.serviceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.DeleteUserByUsernameService;

@ExtendWith(MockitoExtension.class)
public class DeleteUserByEmailServiceTest {

	@Mock
	private OseidUserDetailsRepository oseidUserDetailsRepository;

	@InjectMocks
	private DeleteUserByUsernameService deleteUserByUsernameService;
	
	@Test
	public void givenAnEmail_whenDeleteByEmailCalled_thenRepositoryDeleteByIdShouldBeUseOnce() {
		
		deleteUserByUsernameService.deleteUserByUsername("email");
		verify(oseidUserDetailsRepository, times(1)).deleteById("email");
	}
	
}
