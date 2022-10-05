package com.auth2.oseidclient.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.OseidUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class OseidUserDetailsServiceTest {

	@Mock
	private OseidUserDetailsRepository mockOseidUserDetailsRepository;
	
	@InjectMocks
	private OseidUserDetailsService oseidUserDetailsService;
	
	@Test
	public void givenRegisteredUser_whenLoadUserCalled_thenRegisteredUserShouldBeReturnrned()   {
		
		OseidUserDetails registerededUser = new OseidUserDetails("max");
		registerededUser.setLocked(false);
		registerededUser.setEnabled(true);
		registerededUser.setPassword("pass");
		registerededUser.setRoles("ROLE_USER");
		
		UserDetails expectedUser = new UserDetails() {
			
			@Override
			public boolean isEnabled() {
				return true;
			}
		
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return "max";
			}
			
			@Override
			public String getPassword() {
				return "pass";
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			}
		};
		
		when(mockOseidUserDetailsRepository.findByEmail("max")).thenReturn(Optional.of(registerededUser));
		
		UserDetails foundUser = oseidUserDetailsService.loadUserByUsername("max");
		
		assertThat(foundUser.getUsername()).isEqualTo(expectedUser.getUsername());		

		
	}
	
	@Test
	public void givenNotEnabledUser_whenLoadUserCalled_thenDefaultNotRegisteredUserShouldBeReturned()   {
		
		OseidUserDetails pendingUser = new OseidUserDetails("max");
		pendingUser.setLocked(false);
		pendingUser.setEnabled(false);
		pendingUser.setPassword("pass");
		pendingUser.setRoles("USER");
		
		UserDetails expectedUser = new UserDetails() {
			
			@Override
			public boolean isEnabled() {
				return false;
			}
		
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return "max";
			}
			
			@Override
			public String getPassword() {
				return "pass";
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			}
		};
		
		when(mockOseidUserDetailsRepository.findByEmail("max")).thenReturn(Optional.of(pendingUser));
		
		UserDetails foundUser = oseidUserDetailsService.loadUserByUsername("max");
		
		assertNull(foundUser.getUsername());		

		
	}
	
	@Test
	public void givenNotRegisteredUser_whenLoadUserCalled_thenUserNameNotFoundExceptionShouldBeThrown()   {
		
		when(mockOseidUserDetailsRepository.findByEmail("lam")).thenReturn(Optional.empty());
		
		UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class 
				,() -> {
					@SuppressWarnings("unused")
					UserDetails result = oseidUserDetailsService.loadUserByUsername("lam");
				});
		
		assertThat("lam" + " not found").isEqualTo(e.getMessage());		
		
	}
	
}
