package com.paololauria.bnb.dtos;

import com.paololauria.bnb.model.entities.Amenities;

public class AmenitiesDto {
    private String amenityName;
    private String amenityImage;

    public AmenitiesDto(Amenities amenities){
        amenityName = amenities.getAmenityName();
        amenityImage = amenities.getAmenityImage();
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
