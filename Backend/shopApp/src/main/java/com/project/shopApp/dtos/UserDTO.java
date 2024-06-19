package com.project.shopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "password cannot be bank")
    private String password;
    @JsonProperty("retype_password")
    private String retypePassword;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("facebook_account_id")
    private Long facebookAccountId;
    @JsonProperty("google_account_id")
    private Long googleAccountId;
    @JsonProperty("role_id")
    private Long roleId;
}
