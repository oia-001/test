package com.spring.app.Service;
import com.spring.app.Security.Token;

/** 
 * @author Saloua LAHJOUJI
 * @date 03/01/2021 
 */


public interface TokenService {
    public Token save(Token token);
    public Boolean checkExistsToken(String token);

}
