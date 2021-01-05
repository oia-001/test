package com.spring.app.Security;
 

/** 
 * @author Saloua LAHJOUJI
 * @date 01/01/2021 
 */


public class JwtResponse {
 private String token;

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public JwtResponse(String token) {
	super();
	this.token = token;
}
 
 
}
