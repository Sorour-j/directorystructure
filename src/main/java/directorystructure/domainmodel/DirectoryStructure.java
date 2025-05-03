package directorystructure.domainmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import directorystructure.exceptions.StructureExceptions;
import directorystructure.exceptions.ValidationExceptions;

public class DirectoryStructure {

	private DirectoryNode root;

	private DirectoryStructure(DirectoryNode root) {
		this.root = root;
	}

	// Build a tree structure
	public static DirectoryStructure build(List<Node> nodes) {
		Node root = null;
		detectCycles(nodes);
		Map<Integer, Node> nodeMap;

		try {
			nodeMap = nodes.stream().collect(Collectors.toMap(Node::getId, Function.identity()));
		} catch (IllegalStateException e) {
			throw new IllegalStateException("Duplicate Node IDs found in the input data");
		}

		for (Node node : nodes) {
			int parentId = node.getParentId();

			if (parentId == 0) {
				if (root != null) {
					throw new StructureExceptions.MultipleRootsException("Multiple roots is not allowed");
				}

				root = (DirectoryNode) node;
			} else {
				Node parent = nodeMap.get(parentId);
				if (parent == null) {
					throw new StructureExceptions.InvalidParentException(
							"The directory does not exist wiht id:" + parentId);
				}
				if (!(parent instanceof DirectoryNode)) {
					throw new StructureExceptions.ParentNotDirectoryException("Parent is not a directory");
				}
				((DirectoryNode) parent).addChild(node);
			}
		}
		if (root == null) {
			throw new IllegalStateException("No root is found!");
		}

		for (Node child : nodes) {
			if (child instanceof DirectoryNode) {
				((DirectoryNode) child).calculateSize();
			}
		}
		return new DirectoryStructure((DirectoryNode) root);
	}

	public DirectoryNode getRoot() {
		return this.root;
	}

	public static void detectCycles(List<Node> nodes) {
		// Build child → parent map
		Map<Integer, Integer> childParent = nodes.stream().collect(Collectors.toMap(Node::getId, Node::getParentId));

		for (Node node : nodes) {
			Set<Integer> seen = new HashSet<>();
			Integer current = node.getId();

			while (current != null) {
				if (!seen.add(current)) {
					throw new StructureExceptions.CycleDetectedException(
							"Cycle detected involving node ID: " + node.getId());
				}
				current = childParent.get(current);
			}
		}
	}

}
