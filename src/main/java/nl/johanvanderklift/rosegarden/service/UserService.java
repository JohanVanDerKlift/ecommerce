package nl.johanvanderklift.rosegarden.service;

import nl.johanvanderklift.rosegarden.api.model.LoginBody;
import nl.johanvanderklift.rosegarden.api.model.RegistrationBody;
import nl.johanvanderklift.rosegarden.model.repository.LocalUserRepository;
import nl.johanvanderklift.rosegarden.exception.UserAlreadyExistsException;
import nl.johanvanderklift.rosegarden.model.LocalUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final LocalUserRepository localUserRepository;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public UserService(LocalUserRepository localUserRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserRepository = localUserRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() ||
                localUserRepository.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        };
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return localUserRepository.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserRepository.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
