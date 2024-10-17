package nl.gerimedica.assessment.common;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class FileParsingException extends RuntimeException {
    public FileParsingException(String message, Exception e) {
        super(message);
    }
}
