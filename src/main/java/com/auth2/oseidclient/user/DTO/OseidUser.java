package com.auth2.oseidclient.user.DTO;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OseidUser implements Cloneable{

	@NotBlank @Email
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String fullname;
	//pattern password >8 caractères alphaNumeric + chacun des symboles"[A-Z0-9a-z$&+,:;=?@#|'<>.^*()%!]{5,9}"
	@Pattern( regexp = "(?=.*[A-Z])(?=.*[@$!%*#?&])(?=.*[0-9])[A-Za-z0-9@$!%*#?&]{8,}")
	private String password;
	@NotBlank
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
