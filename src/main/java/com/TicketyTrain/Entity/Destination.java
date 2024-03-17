package com.TicketyTrain.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "from_location")
    @JsonProperty("from")
    private String from;
    @Column(name = "destination_to")
    @JsonProperty("to")
    private String to;
    @OneToOne
    private User user;
    private double price;
    private String section;
    private String seat;



}
