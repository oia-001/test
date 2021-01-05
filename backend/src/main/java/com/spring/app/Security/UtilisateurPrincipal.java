package com.spring.app.Security;


/** 
 * @author Saloua LAHJOUJI
 * @date 03/01/2021 
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.app.Model.Utilisateur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List; 

public class UtilisateurPrincipal implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur user;



	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // Extraire la liste des rôles (ROLE_name)
        this.user.getRoleList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
            authorities.add(authority);
        });
        return authorities;
    }
   
    
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getActive() == 1;
    }
    public UtilisateurPrincipal() {
 		super();
 		// TODO Auto-generated constructor stub
 	}


 	public UtilisateurPrincipal(Utilisateur user) {
 		super();
 		this.user = user;
 	}
}
