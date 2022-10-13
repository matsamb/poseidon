package com.auth2.oseidclient.oseidrule.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteOseidRuleServiceTest {

	@Mock
	private OseidRuleRepository oseidRuleRepository;
	
	@InjectMocks
	private DeleteOseidRuleService deleteOseidRuleService;
	
	@Test
	public void givenAOseidRule_whenDeleteRuleService_thenOseidRuleRepositoryShouldBeUsedOnce() {
		
		OseidRule rule = new OseidRule();
		rule.setId(1);
		deleteOseidRuleService.deleteRule(rule);
		verify(oseidRuleRepository, times(1)).delete(rule);
		
	}
	
}
