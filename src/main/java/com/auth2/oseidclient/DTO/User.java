package com.auth2.oseidclient.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private String role;
	
}
