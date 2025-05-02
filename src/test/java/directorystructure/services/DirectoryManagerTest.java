package directorystructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;

public class DirectoryManagerTest {

	DirectoryNode root;
	DirectoryNode folder;
	FileNode file;
	
	@BeforeEach
    void setUp() {
		root = DirectoryNode.create(2, 0, "folder1", 0.0);
		folder = DirectoryNode.create(3, 2, "folder2", 0.0);
		file = FileNode.create(4, 3, "file1", 20.0, "Secret", 42.0);
		root.addChild(folder);
		folder.addChild(file);
		List<Node> nodes = new ArrayList<>();
		nodes.add(root);
		nodes.add(folder);
		nodes.add(file);		
    }
	
	@Test
	public void nodeToStringTest() {
		String expDir = "name = folder2, type = Directory, size = 20";
		String expFile = "name = file1, type = File, size = 20, classification = Secret, checksum = 42";
		
		assertEquals(expFile, file.toString());
		assertEquals(expDir, folder.toString());
		
	}
	@Test
	public void BuildTreeSuccess() {
		assertEquals("name = folder1, type = Directory, size = 20\n"
				+ " name = folder2, type = Directory, size = 20\n"
				+ "   name = file1, type = File, size = 20, classification = Secret, checksum = 42", StructureManager.BuildTree(nodes));
	}
}
