package quixotic.projects.cryptomanager.exception.badRequestException;


import quixotic.projects.cryptomanager.exception.APIException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends APIException {
    public BadRequestException(String message) {
        super(BAD_REQUEST, message);
    }
}
