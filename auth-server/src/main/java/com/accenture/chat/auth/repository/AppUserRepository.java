package com.accenture.chat.auth.repository;

import com.accenture.chat.auth.model.AppUser;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AppUserRepository {

    private static final Map<String, AppUser> repository = new ConcurrentHashMap<>();

    static {
        repository.put("admin@email.com", new AppUser());
        repository.put("user@email.com", new AppUser());
    }

    public Optional<AppUser> findUserByEmail(String key) {
        return Optional.ofNullable(repository.get(key));
    }

    // TODO: 03/05/2023 implementar la posibilidad de crear un nuevo usuario
}
