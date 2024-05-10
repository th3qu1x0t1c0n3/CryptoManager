package quixotic.projects.cryptomanager.exception.badRequestException;

public class UsernameTakenException extends BadRequestException {
    public UsernameTakenException() {
        super("message.usernameTaken");
    }
}
