package quixotic.projects.cryptomanager.exception.forbiddenRequestExceptions;


import quixotic.projects.cryptomanager.exception.APIException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenRequestException extends APIException {
    public ForbiddenRequestException(String message) {
        super(FORBIDDEN, message);
    }
}
