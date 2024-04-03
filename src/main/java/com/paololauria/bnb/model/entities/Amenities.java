package com.paololauria.bnb.model.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "amenities")
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private Long amenityId;

    @Column(name = "amenity_name", nullable = false)
    private String amenityName;

    @Column(name = "amenity_image", nullable = false)
    private String amenityImage;

    @ManyToMany(mappedBy = "amenities")
    private List<Room> rooms;

    public Long getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Long amenityId) {
        this.amenityId = amenityId;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public void setAmenityName(String amenityName) {
        this.amenityName = amenityName;
    }

    public String getAmenityImage() {
        return amenityImage;
    }

    public void setAmenityImage(String amenityImage) {
        this.amenityImage = amenityImage;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}