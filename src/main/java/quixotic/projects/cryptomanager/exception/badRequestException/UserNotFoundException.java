package quixotic.projects.cryptomanager.exception.badRequestException;

public class UserNotFoundException extends BadRequestException {
    public UserNotFoundException() {
        super("UserNotFoundException");
    }
}
