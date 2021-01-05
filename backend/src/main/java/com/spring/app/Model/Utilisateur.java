package com.spring.app.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** 
 * @author Saloua LAHJOUJI
 * @date 14/12/2020 
 */


@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column
    private String nom;
    @Column
    private String prenom;
    @Column
    private String email;
    @Column
    private int age;
    @Column
    private String password;
    @Column 
    private String numSiret;
    @Column
    private String dateNaissance;
    private int active;
    private String role = ""; 
 

	public Utilisateur() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
         this.password=password;
    }

    public int getActive() {
        return active;
    }

    public String getRole() {
        return role;
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

	public Utilisateur(long id, String username, String nom, String prenom, String email, int age, String password,
			String numSiret, String dateNaissance, int active, String role) {
		super();
		this.id = id;
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.age = age;
		this.password = password;
		this.numSiret = numSiret;
		this.dateNaissance = dateNaissance;
		this.active = active;
		this.role = role;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getNumSiret() {
		return numSiret;
	}


	public void setNumSiret(String numSiret) {
		this.numSiret = numSiret;
	}


	public String getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public void setId(long id) {
		this.id = id;
	}


	public void setActive(int active) {
		this.active = active;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public void setUsername(String username) {
		this.username = username;
	}
    
    
}
