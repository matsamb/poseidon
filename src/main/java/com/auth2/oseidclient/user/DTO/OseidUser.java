package com.auth2.oseidclient.user.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OseidUser implements Cloneable{

	private String email;
	private String username;
	private String fullname;
	
	//patter password 5 à 9 caractères alphaNumeric + chacun des symboles"[A-Z0-9a-z$&+,:;=?@#|'<>.^*()%!]{5,9}"
	@Min(8)
	@Pattern( regexp = "")
	private String password;
	private String role;
	
	public Object clone() {
		OseidUser copy = null;
		
		try {
			copy = (OseidUser) super.clone();
		}catch(CloneNotSupportedException c) {
			c.printStackTrace();
		}
		return copy;
		
	}
	
}
