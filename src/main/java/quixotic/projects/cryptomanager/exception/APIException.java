package quixotic.projects.cryptomanager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class APIException extends RuntimeException{

	private final HttpStatus status;
	private final String message;

	public APIException(HttpStatus status, String message){
		this.status = status;
		this.message = message;
	}
}
