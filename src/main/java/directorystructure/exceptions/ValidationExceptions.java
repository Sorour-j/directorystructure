package directorystructure.exceptions;

/**
 * Contains exception classes related to node validation. These exceptions are
 * used to handle various error conditions that may occur when validating node
 * attributes or finding directories in the structure.
 */
public class ValidationExceptions {

	public static class InvalidNodeAttributeException extends RuntimeException {
		public InvalidNodeAttributeException(String message) {
			super(message);
		}
	}

	public static class NoSuchFolderException extends RuntimeException {
		public NoSuchFolderException(String message) {
			super(message);
		}
	}

}
