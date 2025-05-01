package directorystructure.domainmodel;

import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends Node{

	private List<Node> children = new ArrayList<>();
	
	private DirectoryNode(int id, int parentId, String name, Double size) {
		super(id, parentId, name, size);
	}
	
	public static DirectoryNode create(int id, int parentId, String name, Double size) {
		
		Node.validaId(id);
		Node.validaName(name);
		Node.validateSize(size);
		return new DirectoryNode(id, parentId, name, size);
	}
	
	@Override
	public boolean isDirectory() {
		return true;
	}

	public List<Node> getChildren() {
		return children;
	}
	
	public void addChild(Node child) {
		children.add(child);
	}
	
}
