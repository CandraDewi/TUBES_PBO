package com.kidaro.kael.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pokemon {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private boolean resting;
    private LocalDate restStartDate;

    @JsonBackReference
    @ManyToOne
    private User owner;
}

