package nl.johanvanderklift.rosegarden.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import nl.johanvanderklift.rosegarden.model.LocalUser;
import nl.johanvanderklift.rosegarden.model.repository.LocalUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LocalUserRepository localUserRepository;

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Test
    public void testVerificationTokenNotUsableForLogin() {
        LocalUser user = localUserRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJWT(user);
        Assertions.assertNull(jwtService.getUsername(token), "Verification token should not contain username.");
    }

    @Test
    public void testAuthTokenReturnUsername() {
        LocalUser user = localUserRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        Assertions.assertEquals(user.getUsername(), jwtService.getUsername(token), "Token for auth should contain user's username");
    }

    @Test
    public void testJWTNotGeneratedByUs() {
        String token = JWT.create().withClaim("USERNAME", "UserA").sign(Algorithm.HMAC256("NotTheRealSecret"));
        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getUsername(token));
    }

    @Test
    public void testJWTCorrectlySignedNoIssuer() {
        String token = JWT.create().withClaim("USERNAME", "UserA").sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(MissingClaimException.class, () -> jwtService.getUsername(token));

    }

}
