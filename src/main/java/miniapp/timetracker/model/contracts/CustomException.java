package miniapp.timetracker.model.contracts;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class CustomException extends ResponseStatusException {
    public CustomException(HttpStatusCode status) {
        super(status);
    }

    public CustomException(HttpStatusCode status, String reason) {
        super(status, reason);
    }


}
