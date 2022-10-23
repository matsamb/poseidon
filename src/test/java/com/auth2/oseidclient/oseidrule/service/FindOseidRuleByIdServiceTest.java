package com.auth2.oseidclient.oseidrule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidLeruRepository;

@ExtendWith(MockitoExtension.class)
public class FindOseidRuleByIdServiceTest {

	@Mock
	private OseidLeruRepository oseidLeruRepository;
	
	@InjectMocks
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@Test
	public void givenARegisteredOseidLeru_whenFindById_thenItShouldReturnTheRegisteredLeru() {
		
		OseidLeru expectedRule = new OseidLeru();
		expectedRule.setId(1);
		List<OseidLeru> ruleList = new ArrayList<>();
		ruleList.add(expectedRule);
		
		when(oseidLeruRepository.findAll()).thenReturn(ruleList);
		
		OseidLeru foundRule = findOseidRuleByIdService.findOseidRuleById(1);
		
		assertThat(foundRule).isEqualTo(expectedRule);
	}
	
	@Test
	public void givenANotRegisteredOseidLeru_whenFindById_thenItShouldReturnTheDefaultNotRegisteredLeru() {
		
		OseidLeru notRegisteredRule = new OseidLeru();
		notRegisteredRule.setId(-1);
		List<OseidLeru> ruleList = new ArrayList<>();
		
		when(oseidLeruRepository.findAll()).thenReturn(ruleList);
		
		OseidLeru foundRule = findOseidRuleByIdService.findOseidRuleById(1);
		
		assertThat(foundRule).isEqualTo(notRegisteredRule);
	}
	
}
