package com.kidaro.kael.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {
    @Id @GeneratedValue
    protected Long id;

    @ManyToOne
    @JsonIgnore
    protected User user;

    protected LocalDate date;
    protected double totalPrice;
}

