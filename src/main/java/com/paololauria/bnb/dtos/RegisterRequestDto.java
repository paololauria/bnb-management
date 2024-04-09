package com.paololauria.bnb.dtos;

import com.paololauria.bnb.model.entities.Role;

public class RegisterRequestDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String birthdate;
    private Role role;
    private String image;

    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String firstname, String lastname, String email, String password, String birthdate, String image, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthdate = birthdate;
        this.image = image;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
