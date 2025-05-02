package directorystructure.domainmodel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DirectoryNode extends Node{

	private List<Node> children = new ArrayList<>();
	private List<FileNode> files = new ArrayList<>();
	
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

	@Override
	public String toString() {
			return String.format("name = %s, type = Directory, size = %.0f", this.getName(), this.getSize());
	}

	private List<FileNode> findFiles(){
		
		for (Node child:this.getChildren()) {
			if (child instanceof FileNode) {
				files.add((FileNode)child);
			}
			if (child instanceof DirectoryNode) {
				files.addAll(((DirectoryNode)child).findFiles());
			}
		}
		return files;
	}
	
	public List<FileNode> getFiles(){
		if (files.isEmpty())
			return findFiles();
		return files;
	}
	public void sortChildren() {
		getChildren().sort(Comparator.comparing(Node::getName, String.CASE_INSENSITIVE_ORDER));// sort is case sensitive
		for (Node node: getChildren()) {
			if (node instanceof DirectoryNode) {
				((DirectoryNode)node).sortChildren();
			}
		}
	}
	
	public Double calculateSize() {
		Double calSize = 0.0;
		if (files.isEmpty()) {
			getFiles();
		}
		for (Node node: files) {
			calSize += node.getSize();
		}
		setSize(calSize);
		return calSize;
	}
}
