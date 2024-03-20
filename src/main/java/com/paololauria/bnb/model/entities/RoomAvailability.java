package com.paololauria.bnb.model.entities;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "room_availability")
public class RoomAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "availability_date")
    private LocalDate availabilityDate;
    @Column(name = "is_available")
    private boolean isAvailable;
    public RoomAvailability(){
    }
    public RoomAvailability(Long id, Room room, LocalDate availabilityDate, boolean isAvailable) {
        this.id = id;
        this.room = room;
        this.availabilityDate = availabilityDate;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
