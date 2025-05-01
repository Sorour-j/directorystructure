package directorystructure.domainmodel;

import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends Node{

	private List<Node> children = new ArrayList<>();
	
	private DirectoryNode(String id, String parentId, Node parent, String name, Double size) {
		super(id, parentId, parent, name, size);
	}
	
	public static DirectoryNode create(String id, String parentId, Node parent, String name, Double size) {
		
		Node.validaId(id);
		Node.validaName(name);
		Node.validateParent(parent);
		Node.validateSize(size);
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
