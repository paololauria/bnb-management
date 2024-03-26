package com.paololauria.bnb.dtos;

import com.paololauria.bnb.model.entities.Review;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;
import jakarta.persistence.*;

import java.util.Date;

public class ReviewDto {

    private Long reviewId;
    private String userName;
    private String roomName;
    private Integer rating;
    private String comment;
    private Date timestamp;

    public ReviewDto() {
    }

    public ReviewDto(Review review) {
        this.reviewId = review.getId();
        this.userName = review.getUser().getFirstname();
        this.roomName = review.getRoom().getRoomName();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.timestamp = review.getTimestamp();
    }


    public Review fromDto(Room room, User user){
        Review rw = new Review(this.rating,this.comment);
        rw.setRoom(room);
        rw.setUser(user);
        return rw;
    }
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
