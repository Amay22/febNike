package nike.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.UNAUTHORIZED, reason="Unathorized User Access Denied")
public class UserUnathorizedException extends Exception {
	private static final long serialVersionUID = 5968000547444142953L;
}
