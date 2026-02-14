package com.apnapg.security;

import com.apnapg.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // ================================
    // AUTHORITIES (ROLE → Spring Role)
    // ================================
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    // ================================
    // CREDENTIALS
    // ================================
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Email is login ID
    }
//    @Override
//    public String getUsername() {
//        return user.getId().toString();
//    }

    // ================================
    // ACCOUNT STATUS (REAL VALUES)
    // ================================
    @Override
    public boolean isAccountNonExpired() {
        return true; // Not implementing expiry logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Not implementing credential expiry
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    // ================================
    // EXTRA HELPERS (OPTIONAL)
    // ================================
    public Long getUserId() {
        return user.getId();
    }

    public String getRole() {
        return user.getRole().name();
    }

    public String getAuthProvider() {
        return user.getAuthProvider().name();
    }

    public User getUser() {
        return user;
    }

    public Long getTenantId() {
        return user.getTenant() != null ? user.getTenant().getId() : null;
    }

    public Long getOwnerId() {
        return user.getOwner() != null ? user.getOwner().getId() : null;
    }




}
