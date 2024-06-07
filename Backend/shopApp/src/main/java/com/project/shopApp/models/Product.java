package com.project.shopApp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private Float price;
    @Column(length = 300)
    private String thumbnail;
    private String description;
    @Column(name = "category_id")
    private Long categoryId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
