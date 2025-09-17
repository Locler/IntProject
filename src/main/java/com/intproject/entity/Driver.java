package com.internproject.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)//машина не сущ без водителя
    private List<Car> cars;

    @Column(nullable = false)
    private Boolean deleted = false;
}
