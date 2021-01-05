package com.spring.app.Security;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/** 
 * @author Saloua LAHJOUJI
 * @date 01/01/2021 
 */


@Component
public class JwtProvider implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;
	private final String CLAIMS_SUBJECT="sub";
	private final String CLAIMS_CREATED="created";
	private Key key; 
    private final static String tokenKey = "atypikhouse";

    
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++/
	
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
		claims.put(CLAIMS_CREATED, new Date());
	return Jwts.builder()                // builder() != parser()   
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 360*1000))
			.signWith(SignatureAlgorithm.HS512, Constants.SECRET)
			.compact();
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++// 
	
	public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenKey)
                .parseClaimsJws(token.substring(7))
                .getBody();
    }
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++// 

	public String getUsernameFromToken(String token) {
		// TODO Auto-generated method stub		 
		try {
			Claims claims = Jwts.parser().setSigningKey(Constants.SECRET)
			.parseClaimsJws(token)
			.getBody();
			 return claims.getSubject();
		}catch (Exception ex) { return null;}
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++// 

	public Boolean validateToken(String token, UserDetails userDetails) {
		return (getUsernameFromToken(token).equals(userDetails.getUsername()));
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++// 

	public boolean validateJwt(String jwt) {
		Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
		return true;
	}
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
}
 