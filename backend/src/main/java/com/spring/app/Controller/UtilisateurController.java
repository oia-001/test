package com.spring.app.Controller;
import com.spring.app.Model.Utilisateur;
import com.spring.app.Repository.UtilisateurRepository;
import com.spring.app.Security.JwtProvider; 
import com.spring.app.Service.TokenService;
import com.spring.app.Service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;  
import java.util.List; 

/** 
 * @author Saloua LAHJOUJI
 * @date 20/12/2020 
 */

 

@CrossOrigin 
@RestController
public class UtilisateurController {
 
    @Autowired
    UtilisateurRepository userRep;
    @Autowired
    UtilisateurService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationManager authenManager;
    @Autowired
    JwtProvider jwtProvider;
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\\\\        
    ////+++++++++++++++++++++++  Inscription  ++++++++++++++++++++++++\\\\
    
    
    @PostMapping(value="/register")
    public String inscription(@RequestBody Utilisateur user) {
    	Utilisateur userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            return "desolé. il existe deja !!!";
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return "inscription terminée avec succès";
        }
    }
 
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\\\\        
    ////+++++++++++++++++++++++++  Login  +++++++++++++++++++++++++\\\\
    
    @PostMapping("/login") 
    public String login(String username, String password) {
        String token = null; 
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
        try {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("+++ erreur +++ 1 ");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtProvider.generateToken(userDetails);   
        } catch (AuthenticationException e) {
            logger.warn("+++ erreur +++ ", e.getMessage());
        }
        return token;
    }
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\\\\
    ////++++++++++++++++++++++++++  Logout  ++++++++++++++++++++++++++++++\\\\

   
 
    
    
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\\\\
    ////+++++++++++++++++++  liste des utilisateurs  ++++++++++++++++++++++++\\\\

    
    
    @GetMapping("/users")	
    public List<Utilisateur> listUsers(){
      return userRep.findAll()	;
    }

    
     //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\\\\
     
    
    
}





