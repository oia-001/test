package com.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
/** 
 * @author Saloua LAHJOUJI,
 * @author #####
 * @author #####
 * @author ##### 
 * 
 */

@SpringBootApplication(scanBasePackages = "com.spring.app")
public class AtypikhouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtypikhouseApplication.class, args);

		
               
		/*   tests   */
		
	  	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		String password = "Atypikhouse2021@";
		String encodedPassword = passwordEncoder.encode(password);
		System.out.println();
		System.out.println("Password is         : " + password);
		System.out.println("Encoded Password is : " + encodedPassword);
		boolean isPasswordMatch = passwordEncoder.matches(password, encodedPassword);
		System.out.println("Password : " + password + "   isPasswordMatch    : " + isPasswordMatch);
		System.out.println();
	}

}
