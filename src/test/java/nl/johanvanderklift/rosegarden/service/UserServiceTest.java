package nl.johanvanderklift.rosegarden.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import nl.johanvanderklift.rosegarden.api.model.LoginBody;
import nl.johanvanderklift.rosegarden.api.model.RegistrationBody;
import nl.johanvanderklift.rosegarden.exception.EmailFailureException;
import nl.johanvanderklift.rosegarden.exception.UserAlreadyExistsException;
import nl.johanvanderklift.rosegarden.exception.UserNotVerifiedException;
import nl.johanvanderklift.rosegarden.model.VerificationToken;
import nl.johanvanderklift.rosegarden.model.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @RegisterExtension
    public static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true);

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        body.setFirstName("Firstname");
        body.setLastName("Lastname");
        body.setPassword("MySecretPassword123");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Username should already be in use");
        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@junit.com");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Email should already be in use");

        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
//        Assertions.assertDoesNotThrow(() -> userService.registerUser(body),
//                "User should register successfully");
//
//        Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0]
//                .getRecipients(Message.RecipientType.TO)[0].toString());

    }

    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
        LoginBody body = new LoginBody();
        body.setUsername("UserA-NotExists");
        body.setPassword("PasswordA123-BadPassword");
        Assertions.assertNull((userService.loginUser(body)), "The user should not exist.");
        body.setUsername("UserA");
        Assertions.assertNull(userService.loginUser(body), "The password should be incorrect.");
        body.setPassword("PasswordA123");
        Assertions.assertNotNull(userService.loginUser(body), "The should login successfully.");
        body.setPassword("PasswordA123");
//        try {
//            userService.loginUser(body);
//            Assertions.fail("User should not have email verified");
//        } catch (UserNotVerifiedException e) {
//            Assertions.assertTrue(e.isNewEmailSend(), "Email verification should be send.");
//            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
//        }
//        try {
//            userService.loginUser(body);
//            Assertions.fail("User should not have email verified");
//        } catch (UserNotVerifiedException e) {
//            Assertions.assertFalse(e.isNewEmailSend(), "Email verification should not be resend.");
//            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
//        }
    }

    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"), "Token that is bad or does not exist should return false");
        LoginBody body = new LoginBody();
        body.setUsername("UserB");
        body.setPassword("PasswordB123");
//        try {
//            userService.loginUser(body);
//            Assertions.fail("User should not have email verified");
//        } catch (UserNotVerifiedException e) {
//            List<VerificationToken> tokens = verificationTokenRepository.findByUser_IdOrderByUser_IdDesc(2L);
//            String token = tokens.get(0).getToken();
//            Assertions.assertTrue(userService.verifyUser(token));
//            Assertions.assertNotNull(body, "The user should now be verified");
//        }
    }

}
