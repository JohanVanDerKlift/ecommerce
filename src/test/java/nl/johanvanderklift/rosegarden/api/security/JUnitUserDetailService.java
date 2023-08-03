package nl.johanvanderklift.rosegarden.api.security;

import nl.johanvanderklift.rosegarden.model.LocalUser;
import nl.johanvanderklift.rosegarden.model.repository.LocalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JUnitUserDetailService implements UserDetailsService {

    @Autowired
    private LocalUserRepository localUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> opUser = localUserRepository.findByUsernameIgnoreCase(username);
        if (opUser.isPresent()) {
            return opUser.get();
        }
        return null;
    }
}
