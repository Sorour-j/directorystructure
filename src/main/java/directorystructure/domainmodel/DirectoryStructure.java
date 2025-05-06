
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

/**
 * Provides services for managing directory structures. This class offers
 * methods for building tree representations, filtering files based on security
 * classifications, and calculating size information.
 * 
 * @author Sorour
 * @version 1.0
 */

public class DirectoryStructure {

	private DirectoryNode root;

	private DirectoryStructure(DirectoryNode root) {
		this.root = root;
	}

	/**
	 * Builds a directory structure from a list of nodes. This method organises the
	 * nodes into a hierarchical structure based on their parent-child
	 * relationships.
	 * 
	 * @param nodes The list of nodes to build the structure from
	 * @return A new DirectoryStructure with the organised nodes
	 * @throws IllegalStateException If multiple roots are found, if no root is
	 *                               found, if the root is not a directory, or if a
	 *                               parent is not a directory
	 */

	public static DirectoryStructure build(List<Node> nodes) {

		// Check for cycles first
		detectCycles(nodes);

		// Create a map of nodes by ID
		Map<Integer, Node> nodeMap;
		try {
			nodeMap = nodes.stream().collect(Collectors.toMap(Node::getId, Function.identity()));
		} catch (IllegalStateException e) {
			throw new IllegalStateException("Duplicate Node IDs found in the input data");
		}

		DirectoryNode root = null;

		for (Node node : nodes) {
			int parentId = node.getParentId();

			if (parentId == 0) {
				if (root != null) {
					throw new StructureExceptions.MultipleRootsException("Multiple roots is not allowed");
				}

				if (!(node instanceof DirectoryNode)) {
					throw new StructureExceptions.ParentNotDirectoryException("Root must be a directory");
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

		root.calculateSize();

		return new DirectoryStructure(root);
	}

	public DirectoryNode getRoot() {
		return this.root;
	}

	public static void detectCycles(List<Node> nodes) {
		// Build child-parent map
		Map<Integer, Integer> childParent = nodes.stream().collect(Collectors.toMap(Node::getId, Node::getParentId));

		for (Node node : nodes) {
			Set<Integer> visited = new HashSet<>();
			Integer current = node.getId();

			while (current != null) {
				if (!visited.add(current)) {
					throw new StructureExceptions.CycleDetectedException(
							"Cycle detected involving node ID: " + node.getId());
				}
				current = childParent.get(current);
			}
		}
	}

}
