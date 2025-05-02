package directorystructure.domainmodel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DirectoryStructure {

	 private DirectoryNode root;
	  
	private DirectoryStructure(DirectoryNode root) {
       this.root = root;
    }
	
	// add children to directory nodes
	public static DirectoryStructure build(List<Node> nodes) {
		
		Node root = null;
		Map<Integer, Node> nodeMap = nodes.stream()
	            .collect(Collectors.toMap(Node::getId, Function.identity()));
		
		for (Node node : nodes) {
			int parentId = node.getParentId();
			
			
			if (parentId == 0) {
				if (root != null) {
					throw new IllegalStateException("Multiple roots is not allowed");
				}
				if (!(node instanceof DirectoryNode)) {
					throw new IllegalStateException("Root must be a directory");
				}
				root = (DirectoryNode)node;
			}
			else {
				Node parent = nodeMap.get(parentId);
				if (!(parent instanceof DirectoryNode)) {
					throw new IllegalStateException("Parent is not a directory");
				}
				((DirectoryNode)parent).addChild(node);
			}
		}
		if (root == null) {
			throw new IllegalStateException("No root is found!");
		}
		for (Node child:nodes) {
			if (child instanceof DirectoryNode) {
				((DirectoryNode) child).calculateSize();
			}
		}
		return new DirectoryStructure((DirectoryNode)root);
	}
	
	public DirectoryNode getRoot() {
        return this.root;
    }
}
