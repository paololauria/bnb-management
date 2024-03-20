package com.paololauria.bnb.dtos;

import com.paololauria.bnb.model.entities.User;

import java.time.LocalDate;

public class UserDto {
    private Long id;
    private String firstname;
    private  String lastname;
    private String email;
    private LocalDate birthdate;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.birthdate = user.getBirthdate();
    }

    public User fromDto(){
        return new User(this.id, this.firstname, this.lastname, this.email, this.fromDto().getRole());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
