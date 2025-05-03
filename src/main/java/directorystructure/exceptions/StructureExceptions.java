package directorystructure.exceptions;

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
