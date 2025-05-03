package directorystructure.exceptions;

public class ValidationExceptions {

	    public static class InvalidNodeAttributeException extends RuntimeException {
	        public InvalidNodeAttributeException(String message) {
	            super(message);
	        }
	    }

}
