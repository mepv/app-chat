package com.accenture.chat.auth.service;

import com.accenture.chat.auth.model.AppUser;
import com.accenture.chat.auth.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;

public class UserDetailServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserDetailServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findUserByEmail(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with email %s not found", username)));
        // TODO: 03/05/2023 completar
        return null;
    }
}
