package com.auth2.oseidclient.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidLeruRepository;
import com.auth2.oseidclient.service.DeleteOseidRueService;

@ExtendWith(MockitoExtension.class)
public class DeleteOseidRuleServiceTest {

	@Mock
	private OseidLeruRepository oseidLeruRepository;

	@InjectMocks
	private DeleteOseidRueService deleteOseidRuleService;

	@Test
	public void givenAOseidRule_whenDeleteRuleService_thenOseidRuleRepositoryShouldBeUsedOnce() {

		OseidLeru rule = new OseidLeru();
		rule.setId(1);
		deleteOseidRuleService.deleteOseidRue(rule);
		verify(oseidLeruRepository, times(1)).delete(rule);

	}

}
