package exception;

public class PatientNumberNotFoundException extends Exception {
    // Constructor that accepts a custom error message
    public PatientNumberNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a custom error message and the cause (another exception)
    public PatientNumberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
