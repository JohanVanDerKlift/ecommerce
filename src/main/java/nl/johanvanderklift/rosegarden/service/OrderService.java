package nl.johanvanderklift.rosegarden.service;

import nl.johanvanderklift.rosegarden.model.LocalUser;
import nl.johanvanderklift.rosegarden.model.WebOrders;
import nl.johanvanderklift.rosegarden.model.repository.WebOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final WebOrderRepository webOrderRepository;

    public OrderService(WebOrderRepository webOrderRepository) {
        this.webOrderRepository = webOrderRepository;
    }

    public List<WebOrders> getOrders(LocalUser user) {
        return webOrderRepository.findByUser(user);
    }
}
