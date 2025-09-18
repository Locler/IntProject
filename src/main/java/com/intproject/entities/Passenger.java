package com.intproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="passenger")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Passenger {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private Boolean deleted = false;
}
