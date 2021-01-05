package com.spring.app.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.app.Model.Utilisateur;

/** 
 * @author Saloua LAHJOUJI
 * @date 03/01/2021 
 */

public interface UtilisateurService {
    public Utilisateur findByUsername(String username);
    public void saveUser(Utilisateur user);
	//UserDetails loadUserByUsername(String username); 
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
