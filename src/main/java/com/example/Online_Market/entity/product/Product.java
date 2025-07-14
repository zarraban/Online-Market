package com.example.Online_Market.entity.product;

import com.example.Online_Market.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "cost")
    private Integer cost;

    @NotNull
    @Column(name = "photo_ref")
    private String photo;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Size(min = 2)
    @Column(name = "category")
    private String category;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private Set<User> users;
}
