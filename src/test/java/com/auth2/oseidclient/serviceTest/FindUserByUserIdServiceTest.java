package com.auth2.oseidclient.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.entity.UserId;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.FindUserByUserIdService;

@ExtendWith(MockitoExtension.class)
public class FindUserByUserIdServiceTest {

	@Mock
	private OseidUserDetailsRepository userDetailsRepository;
	
	@InjectMocks
	private FindUserByUserIdService findUserByUserIdService;
	
	@Test
	public void test() {
		
		UserId userId = new UserId(1);
		OseidUserDetails user = new OseidUserDetails();
		user.setEmail("mymail");
		user.setUserId(userId);
		
		List<OseidUserDetails> userList = new ArrayList<>();
		userList.add(user);
		
		when(userDetailsRepository.findAll()).thenReturn(userList);
		
		OseidUserDetails result = findUserByUserIdService.findUserByUserId(1);
		
		assertThat(result).isEqualTo(user);
		
		
	}
	
	@Test
	public void testNoRegisteredUser() {
		
		UserId userId = new UserId(-1);
		OseidUserDetails user = new OseidUserDetails();
		user.setUsername("Not_Registered");
		user.setUserId(userId);
		
		List<OseidUserDetails> userList = new ArrayList<>();
		
		when(userDetailsRepository.findAll()).thenReturn(userList);
		
		OseidUserDetails result = findUserByUserIdService.findUserByUserId(1);
		
		assertThat(result).isEqualTo(user);
		
		
	}
	
	
}
