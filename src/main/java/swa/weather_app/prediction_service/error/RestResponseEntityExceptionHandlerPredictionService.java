package swa.weather_app.prediction_service.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import swa.weather_app.prediction_service.entity.ErrorMessage;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandlerPredictionService extends ResponseEntityExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(RestController.class);
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMessage> BackingServiceResponseHandler(HttpClientErrorException exception){
        ErrorMessage msg = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        LOGGER.warn(String.format("Backing service exception occurred = %s", exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }
    @ExceptionHandler(NotEnoughDataToPredict.class)
    public ResponseEntity<ErrorMessage> BackingServiceResponseHandler(NotEnoughDataToPredict exception){
        ErrorMessage msg = new ErrorMessage(HttpStatus.NO_CONTENT, exception.getMessage());
        LOGGER.warn(String.format(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(msg);
    }


}
