package com.accenture.chat.auth.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class Role implements GrantedAuthority {

    private String name;
    private Set<AppUser> users;

    @Override
    public String getAuthority() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }
}
