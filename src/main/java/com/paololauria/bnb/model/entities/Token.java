package com.paololauria.bnb.model.entities;
import jakarta.persistence.*;
@Entity
public class Token {
    @Id
    @GeneratedValue(generator = "token_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "token_id_generator", sequenceName = "token_seq", allocationSize = 1)
    public Integer id;
    @Column(name = "token", unique = true)
    public String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
    public Token() {
    }
    public Token(Integer id, User user, String token, TokenType tokenType, boolean revoked, boolean expired) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.expired = expired;
    }
    public Token(User user, String jwtToken, TokenType tokenType, boolean revoked, boolean expired) {
        this(null, user, jwtToken, tokenType, revoked, expired);
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public TokenType getTokenType() {
        return tokenType;
    }
    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
    public boolean isRevoked() {
        return revoked;
    }
    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
    public boolean isExpired() {
        return expired;
    }
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}