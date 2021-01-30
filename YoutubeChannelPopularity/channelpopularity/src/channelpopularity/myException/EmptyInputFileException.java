package channelpopularity.myException;

/**
 * Exception thrown when the input file is empty.
 */
    public class EmptyInputFileException extends IllegalArgumentException {
        public EmptyInputFileException(String message) {
            super(message);
        }
    }

