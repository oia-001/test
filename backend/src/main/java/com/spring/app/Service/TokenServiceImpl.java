package com.spring.app.Service; 
import com.spring.app.Repository.TokenRepository;
import com.spring.app.Security.Constants;
import com.spring.app.Security.Token; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import java.util.List;

/** 
 * @author Saloua LAHJOUJI
 * @date 04/01/2021 
 */

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);

    }


    @Override
    public Boolean checkExistsToken(String token) {

        List<Token> bl = tokenRepository.findToken(token.replace(Constants.TOKEN_PREFIX,""));
        return bl.size()>0?true:false;
    }


}
