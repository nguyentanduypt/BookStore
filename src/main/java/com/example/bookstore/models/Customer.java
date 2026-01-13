package com.example.bookstore.models;

import com.example.bookstore.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "\"customer\"")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {

//    private boolean isSpecial;
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerType;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Review> review;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Order> order;
}
