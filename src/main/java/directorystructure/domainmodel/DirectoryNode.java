package directorystructure.domainmodel;

import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends Node{

	private List<Node> children = new ArrayList<>();
	
	private DirectoryNode(String id, String parentId, Node parent, String name, Double size) {
		super(id, parentId, parent, name, size);
	}
	
	public static DirectoryNode create(String id, String parentId, Node parent, String name, Double size) {
		if (id.isEmpty() || id == null) {
	        throw new IllegalArgumentException("Id cannot be empty");
		}
		
		if (name == null || name.isEmpty()) {
	        throw new IllegalArgumentException("Directory name cannot be empty");
		}
		if (parent != null && ! parent.isDirectory()) {
			throw new IllegalArgumentException("Parent of a directory must be another directory ");
		}
		return new DirectoryNode(id, parentId, parent, name, size);
	}
	
	@Override
	public boolean isDirectory() {
		return true;
	}

	public List<Node> getChildren() {
		return children;
	}
	
}
