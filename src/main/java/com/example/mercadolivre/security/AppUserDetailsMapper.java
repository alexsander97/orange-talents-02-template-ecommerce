package com.example.mercadolivre.security;


import com.example.mercadolivre.newUser.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;


@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper{

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new LoggedUser((User) shouldBeASystemUser);
    }

}