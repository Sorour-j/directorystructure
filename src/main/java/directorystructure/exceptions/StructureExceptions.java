package directorystructure.exceptions;

/**
 * Contains exception classes related to directory structure validation. These
 * exceptions are used to handle various error conditions that may occur when
 * building or manipulating directory structures.
 * 
 * @author Sorour
 * @version 1.0
 */

public class StructureExceptions {
 
	public static class CycleDetectedException extends RuntimeException {
		public CycleDetectedException(String message) {
			super(message);
		}
	}

	public static class MultipleRootsException extends RuntimeException {
		public MultipleRootsException(String message) {
			super(message);
		}
	}

	public static class InvalidParentException extends RuntimeException {
		public InvalidParentException(String message) {
			super(message);
		}
	}

	public static class ParentNotDirectoryException extends RuntimeException {
		public ParentNotDirectoryException(String message) {
			super(message);
		}
	}
}
