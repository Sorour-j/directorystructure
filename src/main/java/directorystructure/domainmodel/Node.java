package directorystructure.domainmodel;

import directorystructure.exceptions.ValidationExceptions;

/**
 * Abstract base class for all nodes in a directory structure. This class
 * provides common functionality for both directory and file nodes.
 * 
 * @author Sorour
 * @version 1.0
 */

public abstract class Node {

	private final int id;
	private final int parentId;
	private final String name;
	protected Double size;

	/**
	 * Constructs a new Node with the specified properties.
	 * 
	 * @param id       The unique identifier
	 * @param parentId The parent identifier
	 * @param name     The name
	 * @param size     The size in bytes
	 */

	protected Node(int id, int parentId, String name, Double size) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.size = size;
	}

	public abstract boolean isDirectory();

	public abstract String toString();

	public abstract Double getSize();

	public int getId() {
		return id;
	}

	public int getParentId() {
		return parentId;
	}

	public String getName() {
		return name;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public static void validateName(String name) {
		if (name == null || name.isEmpty()) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Name cannot be empty");
		}

		if (!name.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
			throw new ValidationExceptions.InvalidNodeAttributeException(
					"Name must only contain alphabetic characters and numbers.");
		}
	}

	public static void validateId(int id) {
		if (id < 0) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Id can not be a negative number");
		}
	}

	public static void validateSize(Double size) {
		if (size != null && size < 0) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Size of file can not be a negative number");
		}
	}
}
