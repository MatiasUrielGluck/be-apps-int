package com.uade.beappsint.entity;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.enums.KycStatusEnum;
import com.uade.beappsint.enums.ThemeEnum;
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

    @Column(name = "kyc_status")
    private KycStatusEnum kycStatus;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "complementary_address")
    private String complementaryAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "theme")
    private ThemeEnum theme;

    @Column(name = "is-enabled")
    private Boolean isEnabled;

    @Column(name = "verification_code")
    private String verificationCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_recently_viewed",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> recentlyViewedProducts;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_favorite_products",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> favoriteProducts;

    public Boolean getIsEnabled() {
        return isEnabled == null || isEnabled;
    }

    public CustomerInfoDTO toDto() {
        return CustomerInfoDTO.builder()
                .id(this.id)
                .email(this.email)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .dateOfBirth(this.dateOfBirth)
                .streetName(this.streetName)
                .streetNumber(this.streetNumber)
                .complementaryAddress(this.complementaryAddress)
                .phoneNumber(this.phoneNumber)
                .isAdmin(this.isAdmin)
                .kycStatus(this.kycStatus)
                .theme(this.theme)
                .build();
    }
}
