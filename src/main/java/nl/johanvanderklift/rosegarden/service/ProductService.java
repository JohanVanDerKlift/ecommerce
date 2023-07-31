package nl.johanvanderklift.rosegarden.service;

import nl.johanvanderklift.rosegarden.model.Product;
import nl.johanvanderklift.rosegarden.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
