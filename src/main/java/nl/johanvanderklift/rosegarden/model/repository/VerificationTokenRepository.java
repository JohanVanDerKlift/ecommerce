package nl.johanvanderklift.rosegarden.model.repository;

import nl.johanvanderklift.rosegarden.model.LocalUser;
import nl.johanvanderklift.rosegarden.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends ListCrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);

}
