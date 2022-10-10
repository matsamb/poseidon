package com.auth2.oseidclient.oseidrule.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@ExtendWith(MockitoExtension.class)
public class SaveOseidRuleServiceTest {

	@Mock
	private OseidRuleRepository oseidRuleRepository;
	
	@InjectMocks
	private SaveOseidRuleService saveOseidRuleService;
	
	@Test
	public void givenARule_whenSaveRuleServiceCalled_thenRuleRepositoryShouldBeUsedOnce() {
		
		OseidRule rule = new OseidRule();
		rule.setId(1);
		
		saveOseidRuleService.saveOseidRule(rule);
		
		verify(oseidRuleRepository, times(1)).save(rule);
		
	}
	
}
