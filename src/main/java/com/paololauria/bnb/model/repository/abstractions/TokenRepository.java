package com.paololauria.bnb.model.repository.abstractions;
import com.paololauria.bnb.model.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = """
            SELECT t FROM Token t INNER JOIN User u
            ON t.user.id = u.id
            WHERE u.id = :id AND (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(Long id);
    Optional<Token> findByToken(String token);
}
