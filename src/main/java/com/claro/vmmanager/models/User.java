package com.claro.vmmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbUsers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String id;

    @NotNull(message = "The name can't be Null")
    @NotBlank(message = "The name can't be Blank")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "The email can't be Null")
    @NotBlank(message = "The email can't be Blank")
    @Column(nullable = false)
    @Email
    private String email;

    @NotNull(message = "The password can't be Null")
    @NotBlank(message = "The password can't be Blank")
    @Column(nullable = false)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
