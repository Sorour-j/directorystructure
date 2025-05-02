package directorystructure.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;

public class DirectoryManager {

	private static String tree = "";
	static List<FileNode> filteredNodes = new ArrayList<FileNode>();

	public static String printTree(Node node) {

		DirectoryNode root = (DirectoryNode) node;
		root.getChildren().sort(Comparator.comparing(Node::getName));// sort is case sensitive
		tree = root.toString() + "\n";
		treeIterator(root.getChildren(), 0);
		return tree;
	}

	private static void treeIterator(List<Node> children, int indentation) {
		for (Node child : children) {

			if (child instanceof FileNode) {
				tree += String.format("%s %s \n", " ".repeat(indentation), child.toString());
			} else if (child instanceof DirectoryNode) {
				tree += String.format("%s %s \n", " ".repeat(indentation), child.toString());
				treeIterator(((DirectoryNode) child).getChildren(), indentation + 1);
			}
		}
	}

	public static String filterTopSecretFiles(Node node) {
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		for (FileNode child:filterByClassification(node, "Top Secret")) {
				builder.append(child.toString()+"\n");
		}
		return builder.toString().trim();
	}

	public static String filterSecretFiles(Node node) {
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		for (FileNode child:filterByClassification(node, "Secret")) {
				builder.append(child.toString()+"\n");
		}
		return builder.toString().trim();
	}

	public static String filterSecretOrTopSecretFiles(Node node) {
		filteredNodes = new ArrayList<FileNode>();
		StringBuilder builder = new StringBuilder();
		builder.append(filterSecretFiles(node));
		builder.append("\n");
		builder.append(filterTopSecretFiles(node));
		return builder.toString().trim();
	}

	public static Double getPublicFilesSize(Node node) {
		filteredNodes = new ArrayList<FileNode>();
		List<FileNode> result = filterByClassification(node, "Public");
		Double sum = 0.0;
		for (Node child:result) {
			sum = sum + child.getSize();
		}
		return sum;
	}

	private static List<FileNode> filterByClassification(Node node, String classification) {

		if (node instanceof FileNode file && file.getClassification().equalsIgnoreCase(classification)) {
			filteredNodes.add((FileNode)node);
		}
		if (node instanceof DirectoryNode dir && dir.getChildren().size() > 0) {

			for (Node child : ((DirectoryNode) node).getChildren()) {
				filterByClassification(child, classification);
			}
		}
		return filteredNodes;
	}
}
