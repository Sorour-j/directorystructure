package directorystructure.domainmodel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import directorystructure.exceptions.ValidationExceptions;

/**
 * Represents a directory node in a directory structure. A directory can contain
 * other directories and files as children.
 * 
 * @author Sorour
 * @version 1.0
 */

public class DirectoryNode extends Node {

	private List<Node> children = new ArrayList<>();
	private List<FileNode> files;
	private List<DirectoryNode> folders;

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

		Node.validateId(id);
		Node.validateName(name);
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
		if (child == null) {
			throw new ValidationExceptions.InvalidNodeAttributeException("Child node cannot be null");
		}
		children.add(child);
	}

	@Override
	public String toString() {
		return String.format("name = %s, type = Directory, size = %.0f", this.getName(), this.getSize());
	}

	/**
	 * Finds all file nodes in this directory and its subdirectories.
	 * 
	 * @return A list of all file nodes
	 */

	private List<FileNode> findFiles() {

		files = new ArrayList<>(); // Reset files list to avoid duplicates

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

	/**
	 * Finds all directory nodes in this directory and its subdirectories.
	 * 
	 * @return A list of all directory nodes
	 */

	private List<DirectoryNode> findFolders() {

		folders = new ArrayList<>(); // Reset folders list to avoid duplicates

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
		return findFiles();
	}

	public List<DirectoryNode> getFolders() {
		return findFolders();
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

		Double totalSize = 0.0;

		// Sum sizes of direct children
		for (Node child : getChildren()) {
			if (child instanceof FileNode) {
				totalSize += child.getSize();
			} else if (child instanceof DirectoryNode dirNode) {
				// Recursive calculation for directories
				totalSize += dirNode.calculateSize();
			}
		}

		setSize(totalSize);
		return totalSize;
	}

	@Override
	public Double getSize() {
		return calculateSize();
	}
}
