package com.paololauria.bnb.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paololauria.bnb.model.entities.User;
import java.util.Date;

public class AuthenticationResponseDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    private Date expirationDate;
    private User user;

    public AuthenticationResponseDto() {
    }

    public AuthenticationResponseDto(String accessToken, String refreshToken, User user,Date expirationDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user=user;
        this.expirationDate=expirationDate;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}