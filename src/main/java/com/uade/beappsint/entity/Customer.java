package com.uade.beappsint.entity;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.enums.KycStatusEnum;
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

/**
 * Entity representing a customer.
 * Implements UserDetails for Spring Security integration.
 * Contains customer details such as name, email, password, date of birth, KYC status, address, phone number, and recently viewed products.
 */
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_recently_viewed",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> recentlyViewedProducts;

    /**
     * Returns the authorities granted to the customer.
     *
     * @return an empty list of authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Returns the username used to authenticate the customer.
     *
     * @return the email of the customer.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the customer's account has expired.
     *
     * @return true if the account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the customer's account is locked.
     *
     * @return true if the account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the customer's credentials have expired.
     *
     * @return true if the credentials are non-expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the customer is enabled.
     *
     * @return true if the customer is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Converts the customer entity to a CustomerInfoDTO.
     *
     * @return a CustomerInfoDTO containing the customer's information.
     */
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
                .build();
    }
}
