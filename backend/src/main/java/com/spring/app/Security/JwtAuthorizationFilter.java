package com.spring.app.Security; 
import com.auth0.jwt.JWT; 
import com.spring.app.Model.Utilisateur;
import com.spring.app.Repository.UtilisateurRepository;
import com.spring.app.Service.TokenService;  
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


/** 
 * @author Saloua LAHJOUJI
 * @date 29/12/2020 
 */


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UtilisateurRepository userRepository;

    @Autowired
    private TokenService tokenService; 



    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UtilisateurRepository userRepository, TokenService tokenService) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }
    
    /* doFilterInternal pour Lire l’en-tête autorisation, où le jeton JWT doit être.
       Si l’en-tête ne contient pas bearer ou est nul délégué à Spring impl et exit.
       Si l’en-tête est présent, essayez de saisir le principal utilisateur de 
       la base de données et d’effectuer l’autorisation et Continuer l’exécution du filtre*/ 
      
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {  	
        String header = request.getHeader("Authorization");    
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);     
           System.out.println("+++ doFilterInternal : header == null ou !header.startsWith(\"Bearer\") +++");
            return;
        }
        System.out.println("+++(4) doFilterInternal : ca marche bien +++");   
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);      
        chain.doFilter(request, response);
    }
 
    /*  parse le token et le valider puis Rechercher dans la DB si nous trouvons l’utilisateur par sujet symbolique (nom d’utilisateur)
    Si c’est le cas, saisissez les détails de l’utilisateur et créez un jeton spring auth
    à l’aide d’un nom d’utilisateur, d’un laissez-passer, d’autorités/rôles   */
    
    
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_STRING)
                .replace(Constants.TOKEN_PREFIX,"");
       // TokenService tokenService = new TokenServiceImpl();
        Boolean isToken= tokenService.checkExistsToken(token);
        if ((token != null) && (!isToken)) { 
            String userName = JWT.require(HMAC512(Constants.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            System.out.println("+++(3) getUsernamePasswordAuthentication +++");                    
            if (userName != null) {
            	Utilisateur user = userRepository.findByUsername(userName);
                UtilisateurPrincipal principal = new UtilisateurPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}
