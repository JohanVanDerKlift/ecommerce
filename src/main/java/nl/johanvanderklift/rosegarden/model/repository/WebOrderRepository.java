package nl.johanvanderklift.rosegarden.model.repository;

import nl.johanvanderklift.rosegarden.model.LocalUser;
import nl.johanvanderklift.rosegarden.model.WebOrders;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderRepository extends ListCrudRepository<WebOrders, Long> {
    List<WebOrders> findByUser(LocalUser user);
}
