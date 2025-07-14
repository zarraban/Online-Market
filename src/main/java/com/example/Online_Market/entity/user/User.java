package com.example.Online_Market.entity.user;

import com.example.Online_Market.entity.product.Product;
import com.example.Online_Market.entity.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 2, max = 15)
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 5, max = 15)
    @NotBlank
    private String lastName;

    @Column(name = "phone")
    @NotNull
    @NotBlank
    private String phone;


    @Column(name = "email")
    @NotNull
    @NotBlank
    @Email
    private String email;


    @Column(name = "country")
    @NotNull
    @NotBlank
    private String country;

    @Column(name = "profle_photo")
    @NotNull
    @NotBlank
    private String profilePhoto;


    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;


    @JoinTable(name = "users_products",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"))
    private Set<Product> products;


}
