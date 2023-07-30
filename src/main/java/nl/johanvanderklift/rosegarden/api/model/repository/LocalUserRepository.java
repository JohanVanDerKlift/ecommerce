package nl.johanvanderklift.rosegarden.api.model.repository;

import nl.johanvanderklift.rosegarden.model.LocalUser;
import org.springframework.data.repository.CrudRepository;

public interface LocalUserRepository extends CrudRepository<LocalUser, Long> {
}
