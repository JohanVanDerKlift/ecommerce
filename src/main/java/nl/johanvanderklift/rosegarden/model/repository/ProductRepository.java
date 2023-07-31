package nl.johanvanderklift.rosegarden.model.repository;

import nl.johanvanderklift.rosegarden.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
