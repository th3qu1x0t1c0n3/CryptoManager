package quixotic.projects.cryptomanager.exception.goneRequestException;



import quixotic.projects.cryptomanager.exception.APIException;

import static org.springframework.http.HttpStatus.GONE;

public class GoneRequestException extends APIException {
    public GoneRequestException(String message) {
        super(GONE, message);
    }
}
