package com.auth2.oseidclient.serviceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class SaveOseidUserDetailsServiceTest {

	@Mock
	private OseidUserDetailsRepository oseidUserDetailsRepository;

	@InjectMocks
	private SaveOseidUserDetailsService saveOseidUserDetailsService;
	
	@Test
	public void givenAUser_whenSaveCalled_thenRepositorySaveShouldBeCalledOnce() {
		OseidUserDetails userToSave = new OseidUserDetails();
		saveOseidUserDetailsService.saveUserDetails(userToSave);
		verify(oseidUserDetailsRepository, times(1)).save(userToSave);
	}
	
}
