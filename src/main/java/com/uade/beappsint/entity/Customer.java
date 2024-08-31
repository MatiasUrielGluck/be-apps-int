package com.uade.beappsint.entity;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Column(name = "kycCompleted", columnDefinition = "boolean default false")
    private boolean kycCompleted;

    @Column(name = "is-admin", columnDefinition = "boolean default false")
    private boolean isAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public CustomerInfoDTO toDto() {
        return CustomerInfoDTO.builder()
                .id(this.id)
                .email(this.email)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .dateOfBirth(this.dateOfBirth)
                .isAdmin(this.isAdmin)
                .build();
    }
}
