package sec10_exception.example.CustomException;

public class BalanceInsufficientException extends Exception{
    public BalanceInsufficientException() { }
    public BalanceInsufficientException(String message) {
        super(message);
    }
}
