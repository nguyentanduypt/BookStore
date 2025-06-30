package com.example.bookstore.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "\"customer\"")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private boolean isSpecial;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Review> reviews;
}
