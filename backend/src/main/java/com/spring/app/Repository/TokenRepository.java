package com.spring.app.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.spring.app.Security.Token;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token,String> {
    @Query(value = "SELECT * FROM token b WHERE b.token = :token", nativeQuery = true)
    List<Token> findToken( @Param("token") String token);
}
