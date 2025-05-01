package directorystructure.domainmodel;

public abstract class Node {

	private final String id;
	private final String parentId;
	private final Node parent;
	private final String name;
	private final Double size;
	
	protected Node (String id, String parentId, Node parent, String name, Double size) {
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
}
