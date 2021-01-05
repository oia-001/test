package com.spring.app.Repository;
import com.spring.app.Model.Utilisateur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 
 * @author Saloua LAHJOUJI
 * @date 14/12/2020 
 */

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
	Utilisateur findByUsername(String username);
	Optional<Utilisateur> findByUsernameOrEmail(String username, String email);

}
