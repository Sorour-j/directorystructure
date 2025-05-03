package directorystructure.domainmodel;

import directorystructure.exceptions.ValidationExceptions;

public abstract class Node {

	private final int id;
	private final int parentId;
	private final String name;
	protected Double size;

	protected Node(int id, int parentId, String name, Double size) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.size = size;
	}

	public abstract boolean isDirectory();
	public abstract String toString();
	
	public int getId() {
		return id;
	}

	public int getParentId() {
		return parentId;
	}

	public String getName() {
		return name;
	}

	public Double getSize() {
		return (size != null) ? size : 0.0;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public static void validaName(String name) {
		if (name == null || name.isEmpty()) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Name of  cannot be empty");
		}

		if (!name.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Name must only contain alphabetic characters and numbers.");
		}
	}

	public static void validaId(int id) {
		if (id<0) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Id can not be a negative number");
		}
	}

	public static void validateSize(Double size) {
		if (size != null && size < 0 ) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Size of file can not be a negative number");
		}
	}
}
