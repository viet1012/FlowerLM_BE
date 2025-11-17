package com.example.leminhflowerBE.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "contact")

public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Column(name = "linkaddress")
    private String linkAddress;
    private String contact1;
    private String contact2;
    private String email;
}
