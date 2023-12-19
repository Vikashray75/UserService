package dev.vikash.UserService.Exception;

public class InvalidCredential extends RuntimeException{
    public InvalidCredential() {
    }

    public InvalidCredential(String message) {
        super(message);
    }

    public InvalidCredential(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredential(Throwable cause) {
        super(cause);
    }
}
