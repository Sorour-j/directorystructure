package directorystructure.domainmodel;

public abstract class Node {

	private final String id;
	private final String parentId;
	private final Node parent;
	private final String name;
	private final Double size;

	protected Node(String id, String parentId, Node parent, String name, Double size) {
		this.id = id;
		this.parentId = parentId;
		this.parent = parent;
		this.name = name;
		this.size = size;
	}

	public abstract boolean isDirectory();

	public String getId() {
		return id;
	}

	public String getParentId() {
		return parentId;
	}

	public Node getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public Double getSize() {
		return size;
	}

	public static void validaName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name of  cannot be empty");
		}

		if (!name.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
			throw new IllegalArgumentException("Name must only contain alphabetic characters and numbers.");
		}
	}

	public static void validaId(String id) {
		if (id.isEmpty() || id == null) {
			throw new IllegalArgumentException("Id cannot be empty");
		}
	}

	public static void validateSize(Double size) {
		if (size < 0) {
			throw new IllegalArgumentException("Size of file can not be a negative number");
		}
	}
	
	public static void validateParent(Node parent) {
		if (parent != null && ! parent.isDirectory()) {
			throw new IllegalArgumentException("The parent must be a directory ");
		}
	}
}
