package channelpopularity.myException;

/**
 * Exception thrown when the input is invalid.
 */

public class InvalidInputException extends IllegalArgumentException {

        public InvalidInputException(String message){
            super(message);
        }
    }

