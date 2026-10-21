public class DatabaseOperationException {

    //Constructor with custom message
    public DatabaseOperationException(String message) {
        super(message);
    }

    //constructor with custom message and original error
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
