package com.auth2.oseidclient.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RuleDTO implements Cloneable {

	Integer id;
	@NotBlank
	String name;
	String description;

	public Object clone() {
		RuleDTO copy = null;
		try {
			copy = (RuleDTO)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}
	
}
