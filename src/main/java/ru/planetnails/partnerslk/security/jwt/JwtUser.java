package ru.planetnails.partnerslk.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

public class JwtUser implements UserDetails {
    private final String id;
    private final String name;
    private final String firstName;
    private final  String lastName;
    private final String password;
    private final String email;
    private final Boolean enabled;
    private final LocalDateTime lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(String id,
                   String name,
                   String firstName,
                   String lastName,
                   String password,
                   String email,
                   Boolean enabled,
                   LocalDateTime lastPasswordResetDate,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
