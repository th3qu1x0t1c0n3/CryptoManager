package quixotic.projects.cryptomanager.exception.forbiddenRequestExceptions;

public class InvalidJwtException extends ForbiddenRequestException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
