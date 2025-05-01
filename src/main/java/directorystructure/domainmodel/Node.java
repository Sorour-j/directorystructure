package directorystructure.domainmodel;

public abstract class Node {

	private final int id;
	private final int parentId;
	private final String name;
	private final Double size;

	protected Node(int id, int parentId, String name, Double size) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.size = size;
	}

	public abstract boolean isDirectory();

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

	public static void validaName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name of  cannot be empty");
		}

		if (!name.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
			throw new IllegalArgumentException("Name must only contain alphabetic characters and numbers.");
		}
	}

	public static void validaId(int id) {
		if (id<0) {
			throw new IllegalArgumentException("Id can not be a negative number");
		}
	}

	public static void validateSize(Double size) {
		if (size != null && size < 0 ) {
			throw new IllegalArgumentException("Size of file can not be a negative number");
		}
	}
}
