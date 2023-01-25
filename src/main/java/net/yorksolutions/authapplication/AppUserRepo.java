package net.yorksolutions.authapplication;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepo extends CrudRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
}
