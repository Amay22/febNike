package nike.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Incorrect Title request")
public class TitleBadRequestException  extends Exception {
	private static final long serialVersionUID = 5968000547444142953L;
}

