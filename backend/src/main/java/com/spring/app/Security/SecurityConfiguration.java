package com.spring.app.Security; 
import com.spring.app.Repository.UtilisateurRepository;
import com.spring.app.Service.TokenService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;


/** 
 * @author Saloua LAHJOUJI
 * @date 01/01/2021 
 */


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UtilisateurRepository userRepository;
    @Autowired
    private DataSource dataSource;


    @Autowired
    private TokenService tokenService;
    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .and().inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("pass")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        	http.cors().disable()
        	    .csrf().disable()        
        	    
                //faire appel au filtre JWT Au.. pour récupérer les données d'un utilisateur et générer un token
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, tokenService))      
                .authorizeRequests()             
                .antMatchers(HttpMethod.POST,"/login","/**","/users","/inscription").permitAll()
                .antMatchers(HttpMethod.GET,"/**","/logouts","/users").permitAll()
                .antMatchers("/logements").authenticated()            
                .antMatchers("/api/entreprise/*").hasRole("ENTREPRISE")
                .antMatchers("/api/admin/*").hasRole("ADMIN")
                .antMatchers("/api/user/*").hasRole("USER")
                .anyRequest().authenticated().and()
                .httpBasic();      
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
                   }

}