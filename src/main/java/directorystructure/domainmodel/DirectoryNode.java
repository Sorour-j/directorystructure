package directorystructure.domainmodel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a directory node in a directory structure. A directory can contain
 * other directories and files as children.
 * 
 * @author Sorour
 * @version 1.0
 */

public class DirectoryNode extends Node {

	private List<Node> children = new ArrayList<>();
	private List<FileNode> files = new ArrayList<>();
	private List<DirectoryNode> folders = new ArrayList<>();

	/**
	 * Constructs a new DirectoryNode with the specified properties.
	 * 
	 * @param id       The unique identifier
	 * @param parentId The parent identifier
	 * @param name     The name
	 * @param size     The size in bytes
	 */

	private DirectoryNode(int id, int parentId, String name, Double size) {
		super(id, parentId, name, size);
	}

	/**
	 * Factory method to create a new DirectoryNode.
	 * 
	 * @param id       The unique identifier
	 * @param parentId The parent identifier
	 * @param name     The name
	 * @param size     The size in bytes
	 * @return A new DirectoryNode instance
	 * @throws IllegalArgumentException If any parameters are invalid
	 */

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

	private List<FileNode> findFiles() {

		for (Node child : this.getChildren()) {
			if (child instanceof FileNode) {
				files.add((FileNode) child);
			}
			if (child instanceof DirectoryNode) {
				files.addAll(((DirectoryNode) child).findFiles());
			}
		}
		return files;
	}
	
	private List<DirectoryNode> findFolders() {

		for (Node child : this.getChildren()) {
			if (child instanceof DirectoryNode) {
				folders.add((DirectoryNode) child);
				folders.addAll(((DirectoryNode) child).findFolders());
			}
		}
		return folders;
	}


	/**
	 * Gets all file nodes that are children of this directory.
	 * 
	 * @return A list of file nodes
	 */

	public List<FileNode> getFiles() {
		if (files.isEmpty())
			return findFiles();
		return files;
	}
	
	public List<DirectoryNode> getFolders() {
		if (folders.isEmpty())
			return findFolders();
		return folders;
	}

	public void sortChildren() {
		getChildren().sort(Comparator.comparing(Node::getName, String.CASE_INSENSITIVE_ORDER));// sort is case sensitive
		for (Node node : getChildren()) {
			if (node instanceof DirectoryNode) {
				((DirectoryNode) node).sortChildren();
			}
		}
	}

	/**
	 * Calculates the total size of this directory by summing the sizes of all
	 * children.
	 * 
	 * @return The total size of the directory
	 */
	public Double calculateSize() {
		Double calSize = 0.0;
		if (files.isEmpty()) {
			getFiles();
		}
		for (Node node : files) {
			calSize += node.getSize();
		}
		setSize(calSize);
		return calSize;
	}
}
