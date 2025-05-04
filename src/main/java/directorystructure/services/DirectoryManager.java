package directorystructure.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.DirectoryStructure;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;

public class DirectoryManager {

	private static String tree = "";
	static List<FileNode> filteredNodes = new ArrayList<FileNode>();

	public static String buildTree(DirectoryStructure structure) {

		DirectoryNode root = structure.getRoot();
		root.sortChildren();
		tree = root.toString() + "\n";
		treeIterator(root.getChildren(), 0);
		return tree.trim();
	}

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

	public static String filterTopSecretFiles(DirectoryStructure structure) {
		DirectoryNode root = structure.getRoot();
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		
		for (FileNode child : filterByClassification(root, "Top Secret")) {
			builder.append(child.toString() + "\n");
		}
		return builder.toString().trim();
	}

	public static String filterSecretFiles(DirectoryStructure structure) {
		DirectoryNode root = structure.getRoot();
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		for (FileNode child : filterByClassification(root, "Secret")) {
			builder.append(child.toString() + "\n");
		}
		return builder.toString().trim();
	}

	public static String filterSecretOrTopSecretFiles(DirectoryStructure structure) {
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		builder.append(filterSecretFiles(structure));
		builder.append("\n");
		builder.append(filterTopSecretFiles(structure));
		return builder.toString().trim();
	}

	public static Double getPublicFilesSize(DirectoryStructure structure) {
		DirectoryNode root = structure.getRoot();
		filteredNodes = new ArrayList<FileNode>();
		List<FileNode> result = filterByClassification(root, "Public");
		Double sum = 0.0;
		for (Node child : result) {
			sum = sum + child.getSize();
		}
		return sum;
	}

	public static String getNonPublicFiles(DirectoryStructure structure, String folderName) {
		DirectoryNode folder = null;
		DirectoryNode root = structure.getRoot();
		
		for (Node child : root.getChildren())
			if (child.getName().equals(folderName)) {
				folder = (DirectoryNode) child;
				break;
			}
		List<FileNode> result = filterByClassification(root, "Public");

		List<FileNode> filtered = folder.getFiles().stream().filter(file -> !result.contains(file))
				.collect(Collectors.toList());

		sortFiles(filtered);
		StringBuilder builder = new StringBuilder();
		for (FileNode child : filtered) {
			builder.append(child.toString() + "\n");
		}
		return builder.toString().trim();
	}

	private static List<FileNode> filterByClassification(Node node, String classification) {

		if (node instanceof FileNode file && file.getClassification().equalsIgnoreCase(classification)) {
			filteredNodes.add((FileNode) node);
		}
		if (node instanceof DirectoryNode dir && dir.getChildren().size() > 0) {

			for (Node child : ((DirectoryNode) node).getChildren()) {
				filterByClassification(child, classification);
			}
		}
		sortFiles(filteredNodes);
		return filteredNodes;
	}
	
	private static void sortFiles(List<FileNode> files) {
		files.sort(Comparator.comparing(Node::getName, String.CASE_INSENSITIVE_ORDER));// sort is case insensitive
	}
}
