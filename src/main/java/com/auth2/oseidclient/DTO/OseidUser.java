package com.auth2.oseidclient.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OseidUser implements Cloneable{

	private String email;
//	private String username;
//	private String name;
	
	//patter password 5 à 9 caractères alphaNumeric + chacun des symboles"[A-Z0-9a-z$&+,:;=?@#|'<>.^*()%!]{5,9}"
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
