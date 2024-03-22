package com.paololauria.bnb.dtos;

import com.paololauria.bnb.model.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private Long id;
    private String firstname;
    private  String lastname;
    private String image;
    private String email;
    private LocalDate birthdate;
    private List<BookingRequestDto> bookings;


    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.image = user.getImage();
        this.email = user.getEmail();
        this.birthdate = user.getBirthdate();
        this.bookings = user.getBookings().stream().map(BookingRequestDto::new).collect(Collectors.toList());
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public List<BookingRequestDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingRequestDto> bookings) {
        this.bookings = bookings;
    }
}
