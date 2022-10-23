package com.auth2.oseidclient.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidLeruRepository;

@ExtendWith(MockitoExtension.class)
public class OseidLeruServiceTest {

	@Mock
	private OseidLeruRepository oseidLeruRepository;
	
	@InjectMocks
	private OseidLeruService oseidLeruService;
	

	@Test
	public void givenARule_whenSaveRuleServiceCalled_thenRuleRepositoryShouldBeUsedOnce() {
		
		OseidLeru leru = new OseidLeru();
		leru.setName("massi");
		
		OseidLeru leru2 = new OseidLeru();
		leru2.setId(1);
		leru2.setName("massi");
		
		when(oseidLeruRepository.saveAndFlush(leru)).thenReturn(leru2);
		
		Integer leruReturned = oseidLeruService.saveOseidLeru(leru);
		
		assertThat(leruReturned).isEqualTo(leru2.getId());
		verify(oseidLeruRepository, times(1)).save(leru);
		
	}
	
	@Nested
	@DisplayName("findLeru")
	class findLeru{
		
		@Test
		public void givenARegisteredOseidLeru_whenFindById_thenItShouldReturnTheRegisteredLeru() {
			
			OseidLeru expectedLeru = new OseidLeru();
			expectedLeru.setId(1);
			List<OseidLeru> leruList = new ArrayList<>();
			leruList.add(expectedLeru);
			
			when(oseidLeruRepository.findAll()).thenReturn(leruList);
			
			OseidLeru foundLeru = oseidLeruService.findOseidLeruById(1);
			
			assertThat(foundLeru).isEqualTo(expectedLeru);
		}
		
		@Test
		public void givenANotRegisteredOseidLeru_whenFindById_thenItShouldReturnTheDefaultNotRegisteredLeru() {
			
			OseidLeru notRegisteredLeru = new OseidLeru();
			notRegisteredLeru.setId(-1);
			List<OseidLeru> leruList = new ArrayList<>();
			
			when(oseidLeruRepository.findAll()).thenReturn(leruList);
			
			OseidLeru foundLeru = oseidLeruService.findOseidLeruById(1);
			
			assertThat(foundLeru).isEqualTo(notRegisteredLeru);
		}
		
	}
	
	@Test
	public void givenAOseidRule_whenDeleteRuleService_thenOseidRuleRepositoryShouldBeUsedOnce() {
		
		OseidLeru leru = new OseidLeru();
		leru.setId(1);
		oseidLeruService.deleteOseidLeru(leru.getId());
		verify(oseidLeruRepository, times(1)).delete(leru);
		
	}
	
	
	
}
