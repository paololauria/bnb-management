package com.paololauria.bnb.model.entities;
import jakarta.persistence.*;
public class Amenities {
    @Entity
    @Table(name = "amenities")
    public class Amenity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "amenity_id")
        private Long amenityId;

        @Column(name = "room_id")
        private Long roomId;

        @Column(name = "amenity_name", nullable = false)
        private String amenityName;

        @Column(name = "amenity_image", nullable = false)
        private String amenityImage;

        public Amenity() {
        }

        public Long getAmenityId() {
            return amenityId;
        }

        public void setAmenityId(Long amenityId) {
            this.amenityId = amenityId;
        }

        public Long getRoomId() {
            return roomId;
        }

        public void setRoomId(Long roomId) {
            this.roomId = roomId;
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
    }
}
