package nl.johanvanderklift.rosegarden.service;

import nl.johanvanderklift.rosegarden.api.model.RegistrationBody;
import nl.johanvanderklift.rosegarden.model.LocalUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public LocalUser registerUser(RegistrationBody registrationBody) {
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        //TODO: Encrypt passwords!!
        user.setPassword(registrationBody.getPassword());
        ret
    }
}
