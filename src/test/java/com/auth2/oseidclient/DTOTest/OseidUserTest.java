package com.auth2.oseidclient.DTOTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.auth2.oseidclient.user.DTO.OseidUser;

public class OseidUserTest {

	@Test
	public void givenReferenceUser_whenClonedAndModified_thenCopyAndReferenceShouldNotBeEqual() {

		OseidUser reference = new OseidUser();

		reference.setEmail("tax");
		reference.setPassword("pass");
		reference.setRole("USER");

		OseidUser copyToModify = (OseidUser) reference.clone();

		copyToModify.setEmail("flex");
		copyToModify.setPassword("busta");
		copyToModify.setRole("ADMIN");

		assertThat(copyToModify).isNotEqualTo(reference);
	}

	@Test
	public void givenReferenceUser_whenClonedAndModified_thenModifiedCopyEqualsMethodShouldReturnFalse() {

		OseidUser reference = new OseidUser();

		reference.setEmail("tax");
		reference.setPassword("pass");
		reference.setRole("USER");

		OseidUser copyToModify = (OseidUser) reference.clone();

		copyToModify.setEmail("flex");
		copyToModify.setPassword("busta");
		copyToModify.setRole("ADMIN");

		assertFalse(copyToModify.equals(reference));

	}

}
