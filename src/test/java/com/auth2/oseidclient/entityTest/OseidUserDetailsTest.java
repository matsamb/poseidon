package com.auth2.oseidclient.entityTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import com.auth2.oseidclient.user.entity.OseidUserDetails;

//@ExtendWith(MockitoExtension.class)
public class OseidUserDetailsTest {

	@Test
	public void givenReferenceUser_whenClonedAndTheCopyAttributesAreModified_thenReferenceAndCopyHaveDifferentsAttributes() {
		
		OseidUserDetails reference = new OseidUserDetails();
		reference.setEmail("mmm");
		
		HashMap<String, Object> att = new HashMap<>();
		att.put("email", "mmm");
		att.put("name", "lol");
		att.put("username", "zoll");
		
		reference.setAttributes(att);
		
		OseidUserDetails copy = (OseidUserDetails) reference.clone();
		
		HashMap<String, Object> attCopy = new HashMap<>();
		attCopy.put("email", "copy");
		attCopy.put("name", "copy");
		attCopy.put("username", "copy");
		
		copy.setAttributes(attCopy);
		
		assertThat(copy.getAttributes()).isNotEqualTo(reference.getAttributes());
		
		
	}
	
	@Test
	public void givenReference_whenGetAttributesCalledAndModified_thenReferenceAttributesAndModifiedAttributesShouldBeDifferents() {
		
		OseidUserDetails reference = new OseidUserDetails();
		reference.setEmail("mmm");
		
		HashMap<String, Object> att = new HashMap<>();
		att.put("email", "mmm");
		att.put("name", "lol");
		att.put("username", "zoll");
		
		Map<String, Object> referenceAttToModify = reference.getAttributes();
		
		referenceAttToModify.replace("email", "fill");
		referenceAttToModify.replace("name", "zoll");
		referenceAttToModify.replace("username", "bright");
		
		assertThat(referenceAttToModify).isNotEqualTo(reference.getAttributes());
		
	}
	
	@Test
	public void givenReferenceUser_whenCloned_thenRefenceEqualsCopyShouldBeTrue() {
		
		OseidUserDetails reference = new OseidUserDetails();
		reference.setEmail("mmm");
		
		HashMap<String, Object> att = new HashMap<>();
		att.put("email", "mmm");
		att.put("name", "lol");
		att.put("username", "zoll");
		
		reference.setAttributes(att);
		
		OseidUserDetails copy = (OseidUserDetails) reference.clone();

		Boolean result = reference.equals(copy);
		
		assertTrue(result);
		
	}
	
}
