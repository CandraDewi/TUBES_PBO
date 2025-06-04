package com.kidaro.kael.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionItem {
    @JsonIgnore
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Item item;

    @JsonBackReference
    @ManyToOne
    private ItemPurchaseTransaction transaction;

    private int quantity;
}

