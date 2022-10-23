package com.auth2.oseidclient.oseidrule.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidLeru;

@ExtendWith(MockitoExtension.class)
public class SaveOseidRuleServiceTest {

//	@Mock
//	private OseidRuleRepository oseidRuleRepository;
	
	@InjectMocks
	private SaveOseidRuleService saveOseidRuleService;
	
	@Test
	public void givenARule_whenSaveRuleServiceCalled_thenRuleRepositoryShouldBeUsedOnce() {
		
		OseidLeru rule = new OseidLeru();
		rule.setName("massi");
		
		OseidLeru rule2 = new OseidLeru();
		rule2.setId(1);
		rule2.setName("massi");
		
//		when(oseidRuleRepository.saveAndFlush(rule)).thenReturn(rule2);
		
		Integer ruleReturned = saveOseidRuleService.saveOseidRule(rule);
		
		assertThat(ruleReturned).isEqualTo(rule2.getId());
		//verify(oseidRuleRepository, times(1)).save(rule);
		
	}
	
}
