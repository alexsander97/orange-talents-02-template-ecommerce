package com.example.mercadolivre.security;


import com.example.mercadolivre.newUser.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public class LoggedUser implements UserDetails {

    private User user;
    private org.springframework.security.core.userdetails.User springUserDetails;

    public LoggedUser(@NotNull @Valid User user) {
        this.user = user;
        springUserDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return springUserDetails.getAuthorities();
    }

    public String getPassword() {
        return springUserDetails.getPassword();
    }

    public String getUsername() {
        return springUserDetails.getUsername();
    }

    public boolean isEnabled() {
        return springUserDetails.isEnabled();
    }

    public boolean isAccountNonExpired() {
        return springUserDetails.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return springUserDetails.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return springUserDetails.isCredentialsNonExpired();
    }

    public User get() {
        return user;
    }

}