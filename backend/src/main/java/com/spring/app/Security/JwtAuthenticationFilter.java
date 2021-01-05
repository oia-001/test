package com.spring.app.Security;

/** 
 * @author Saloua LAHJOUJI
 * @date 28/12/2020 
 */

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    //constructeur avec champs de la classe JWTAuthenticationFilter pour qu'il soit utilisé
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /* Déclencheur lorsque nous émettons une demande POST à /login
       Nous devons également passer dans {"nom d’utilisateur »:"username"», 
       « mot de passe »:"password"} dans le corps de demande
     
    
       Saisissez les informations d’identification et cartographiez-les pour connecter viewmodel
     puis Créer le token de connexion    */
      
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	LoginRequest credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());
        // Authentifier l’utilisateur
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        System.out.println("+++(1) tu es dans: attemptAuthentication +++");
        return auth;
    }
    
    
    /*
         on Crée JWT Token puis on ajoute header avec le préfixe et jwt crypté dans le httpResponse
     */
    
    
    @Override            
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {    
       System.out.println(authResult.getPrincipal());
        //  UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .sign(HMAC512(Constants.SECRET.getBytes()));
        System.out.println("+++(2)  tu es dans fct: successfulAuthentication +++ token : "+token); 
        response.addHeader(Constants.HEADER_STRING, Constants.TOKEN_PREFIX +  token);
    }

}
