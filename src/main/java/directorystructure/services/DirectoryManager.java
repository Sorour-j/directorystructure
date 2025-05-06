package directorystructure.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.DirectoryStructure;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;
import directorystructure.exceptions.StructureExceptions;
import directorystructure.exceptions.ValidationExceptions;

/**
 * Provides services for managing directory structures. This class offers
 * methods for building tree representations, and filtering files based on
 * security classifications.
 * 
 * @author Sorour
 * @version 1.0
 */

public class DirectoryManager {

	private static String tree = "";
	static List<FileNode> filteredNodes = new ArrayList<FileNode>();

	/**
	 * Builds a string representation of the directory tree structure.
	 * 
	 * @param structure The directory structure to build the tree from
	 * @return A formatted string representation of the directory tree
	 */

	public static String buildTree(DirectoryStructure structure) {

		DirectoryNode root = structure.getRoot();
		root.calculateSize();
		root.sortChildren();
		tree = root.toString() + "\n";
		treeIterator(root.getChildren(), 0);
		return tree.trim();
	}

	/**
	 * Helper method to recursively iterate through the directory tree.
	 * 
	 * @param children    List of child nodes to process
	 * @param indentation Current indentation level
	 */

	private static void treeIterator(List<Node> children, int indentation) {
		for (Node child : children) {

			if (child instanceof FileNode) {
				tree += String.format("%s %s\n", " ".repeat(indentation), child.toString());
			} else if (child instanceof DirectoryNode) {
				tree += String.format("%s %s\n", " ".repeat(indentation), child.toString());
				treeIterator(((DirectoryNode) child).getChildren(), indentation + 1);
			}
		}
	}

	/**
	 * Filters and returns all files classified as "Top Secret".
	 * 
	 * @param structure The directory structure to filter
	 * @return A string containing all "Top Secret" files, one per line
	 */

	public static String filterTopSecretFiles(DirectoryStructure structure) {
		DirectoryNode root = structure.getRoot();
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();

		for (FileNode child : filterByClassification(root, "Top secret")) {
			builder.append(child.toString() + "\n");
		}
		return builder.toString().trim();
	}

	/**
	 * Filters and returns all files classified as "Secret".
	 * 
	 * @param structure The directory structure to filter
	 * @return A string containing all "Secret" files, one per line
	 */

	public static String filterSecretFiles(DirectoryStructure structure) {
		DirectoryNode root = structure.getRoot();
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		for (FileNode child : filterByClassification(root, "Secret")) {
			builder.append(child.toString() + "\n");
		}
		return builder.toString().trim();
	}

	/**
	 * Filters and returns all files classified as "Top Secret" or "Secret".
	 * 
	 * @param structure The directory structure to filter
	 * @return A string containing all "Top Secret" files, one per line
	 */
	public static String filterSecretOrTopSecretFiles(DirectoryStructure structure) {
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		builder.append(filterSecretFiles(structure));
		builder.append("\n");
		builder.append(filterTopSecretFiles(structure));
		return builder.toString().trim();
	}

	/**
	 * Calculates the total size of all files classified as "Public".
	 * 
	 * @param structure The directory structure to calculate from
	 * @return The total size of all public files
	 */

	public static Double getPublicFilesSize(DirectoryStructure structure) {
		DirectoryNode root = structure.getRoot();
		filteredNodes = new ArrayList<FileNode>();
		List<FileNode> result = filterByClassification(root, "Public");

		return result.stream().mapToDouble(Node::getSize).sum();

	}

	/**
	 * Retrieves all non-public files within a specific folder.
	 * 
	 * @param structure  The directory structure to search
	 * @param folderName The name of the folder to search in
	 * @return A string containing all non-public files in the specified folder
	 */
	public static String getNonPublicFiles(DirectoryStructure structure, String folderName) {
		DirectoryNode root = structure.getRoot();

		// Find the folder
		DirectoryNode folder = null;
		for (DirectoryNode child : root.getFolders()) {
			if (child.getName().equals(folderName)) {
				folder = child;
				break;
			}
		}

		if (folder == null) {
			throw new ValidationExceptions.NoSuchFolderException(folderName + " does not exist");
		}

		// Get all files in the target folder
		List<FileNode> folderFiles = folder.getFiles();

		// Filter non-public files
		List<FileNode> nonPublicFiles = folderFiles.stream().filter(file -> !"Public".equals(file.getClassification()))
				.sorted(Comparator.comparing(Node::getName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());

		StringBuilder builder = new StringBuilder();
		for (FileNode child : nonPublicFiles) {
			builder.append(child.toString() + "\n");
		}
		return builder.toString().trim();
	}

	/**
	 * Helper method to filter files by classification.
	 * 
	 * @param root           The root directory node to start filtering from
	 * @param classification The classification to filter by
	 * @return A list of file nodes matching the specified classification
	 */

	private static List<FileNode> filterByClassification(DirectoryNode root, String classification) {

		List<FileNode> filteredNodes = root.getFiles().stream()
				.filter(file -> classification.equals(file.getClassification()))
				.sorted(Comparator.comparing(Node::getName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());

		return filteredNodes;
	}
}
