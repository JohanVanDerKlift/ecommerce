package nl.johanvanderklift.rosegarden.exception;

public class UserNotVerifiedException extends Exception {

    private boolean newEmailSend;

    public UserNotVerifiedException(boolean newEmailSend) {
        this.newEmailSend = newEmailSend;
    }

    public boolean isNewEmailSend() {
        return newEmailSend;
    }
}
