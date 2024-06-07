package com.project.shopApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullname",length = 100)
    private String fullName;
    @Column(name = "phone_number",length = 10,nullable = false)
    private String phoneNumber;
    private String address;
    @Column(name = "password",length = 200)
    private String password;
    @Column(name = "is_active")
    private Boolean active;
    @Column(name = "date_of_birth")
    private Date dateofBirth;
    @Column(name = "facebook_account_id")
    private Long facebookAccountId;
    @Column(name = "google_account_id")
    private Long googleAccountId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
