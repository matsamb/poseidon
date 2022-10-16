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
import com.auth2.oseidclient.repository.OseidRuleRepository;

@ExtendWith(MockitoExtension.class)
public class FindOseidRuleByIdServiceTest {

	@Mock
	private OseidRuleRepository oseidRuleRepository;
	
	@InjectMocks
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@Test
	public void givenARegisteredOseidRule_whenFindById_thenItShouldReturnTheRegisteredRule() {
		
		OseidLeru expectedRule = new OseidLeru();
		expectedRule.setId(1);
		List<OseidLeru> ruleList = new ArrayList<>();
		ruleList.add(expectedRule);
		
		when(oseidRuleRepository.findAll()).thenReturn(ruleList);
		
		OseidLeru foundRule = findOseidRuleByIdService.findOseidRuleById(1);
		
		assertThat(foundRule).isEqualTo(expectedRule);
	}
	
	@Test
	public void test1() {
		
		OseidLeru notRegisteredRule = new OseidLeru();
		notRegisteredRule.setId(-1);
		List<OseidLeru> ruleList = new ArrayList<>();
		
		when(oseidRuleRepository.findAll()).thenReturn(ruleList);
		
		OseidLeru foundRule = findOseidRuleByIdService.findOseidRuleById(1);
		
		assertThat(foundRule).isEqualTo(notRegisteredRule);
	}
	
}
