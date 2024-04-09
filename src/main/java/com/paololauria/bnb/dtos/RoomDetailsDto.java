package com.paololauria.bnb.dtos;
import com.paololauria.bnb.model.entities.Room;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public class RoomDetailsDto {
    private Long roomId;
    private String roomName;
    private Integer maxGuest;
    private BigDecimal pricePerNight;
    private String roomCover;
    private String description;
    private double averageRating;
    private String location;
    private List<AmenitiesDto> amenities;


    public RoomDetailsDto(){

    }

    public RoomDetailsDto(Room room){
        roomId = room.getRoomId();
        roomName = room.getRoomName();
        maxGuest = room.getMaxGuest();
        pricePerNight = room.getPricePerNight();
        roomCover = room.getRoomCover();
        description = room.getDescription();
        averageRating = Optional.ofNullable(room.calculateAverageRating()).orElse(0.0);
        location = room.getLocation();
        amenities = room.getAmenities().stream().map(AmenitiesDto::new).collect(Collectors.toList());
    }


    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getMaxGuest() {
        return maxGuest;
    }

    public void setMaxGuest(Integer maxGuest) {
        this.maxGuest = maxGuest;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getRoomCover() {
        return roomCover;
    }

    public void setRoomCover(String roomCover) {
        this.roomCover = roomCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<AmenitiesDto> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<AmenitiesDto> amenities) {
        this.amenities = amenities;
    }
}
