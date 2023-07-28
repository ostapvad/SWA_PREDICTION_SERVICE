package swa.weather_app.prediction_service.error;

public class NotEnoughDataToPredict extends  Exception{
    public NotEnoughDataToPredict() {
        super();
    }

    public NotEnoughDataToPredict(String message) {
        super(message);
    }

    public NotEnoughDataToPredict(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughDataToPredict(Throwable cause) {
        super(cause);
    }

    protected NotEnoughDataToPredict(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
