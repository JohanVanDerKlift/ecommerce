package nl.johanvanderklift.rosegarden.model.repository;

import nl.johanvanderklift.rosegarden.model.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface LocalUserRepository extends ListCrudRepository<LocalUser, Long> {
    Optional<LocalUser> findByUsernameIgnoreCase(String username);

    Optional<LocalUser> findByEmailIgnoreCase(String email);



}
