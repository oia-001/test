package com.spring.app.Service;
import com.spring.app.Model.Utilisateur; 
import com.spring.app.Repository.UtilisateurRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; 
import java.util.List;

/** 
 * @author Saloua LAHJOUJI
 * @date 03/01/2021 
 */


@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository userRepository;

    @Override
    public Utilisateur findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
  
   // ++++++++++++++++++++++++++++++++  30/12/2020 ++++++++++++++++++++++++++++++++ \\
    
  
	@Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Utilisateur user = userRepository.findByUsername(username);
                if(user==null) {  
                	System.out.println("+++ User not found +++");
                	new UsernameNotFoundException("User not found");
                    return null; }
                else {
                	System.out.println("+++ User found +++");
                    System.out.println("loadUserByUsername "+user.toString()+" Role: "+user.getRole());
                    List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("USER", "ADMIN");
                    List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("USER");
                    return new org.springframework.security.core.userdetails.User
                            (user.getEmail(), user.getPassword(), user.getRole()=="ADMIN" ? authorityListAdmin : authorityListUser);
                }
    }
    
     
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \\
	
    @Override
    public void saveUser(Utilisateur user) {
        userRepository.save(user);
    }

}
