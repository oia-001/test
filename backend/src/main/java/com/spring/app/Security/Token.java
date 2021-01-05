package com.spring.app.Security;

/** 
 * @author Saloua LAHJOUJI
 * @date 03/01/2021 
 */


import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name="Token")
    public String token;
	 
 
}
