package quixotic.projects.cryptomanager.exception.badRequestException;

public class WrongUserException extends BadRequestException{
    public WrongUserException() {
        super("message.wrongUser");
    }
}
