package eci.arsw.covidanalyzer.exceptions;

public class CovidAggregateException extends Exception{
    public CovidAggregateException(String message) {
        super(message);
    }

    public CovidAggregateException(String message, Throwable cause) {
        super(message, cause);
    }

}
