package com.project.shopApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "social_accounts")
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "provider",nullable = false,length = 20)
    private String provider;
    @Column(name = "provider_id",nullable = false,length = 50)
    private String providerId;
    private String name;
    private String email;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
