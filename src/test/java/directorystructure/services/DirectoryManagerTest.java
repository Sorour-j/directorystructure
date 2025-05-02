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
	FileNode file, file2;
	List<Node> nodes = new ArrayList<>();
	
	@BeforeEach
    void setUp() {
		root = DirectoryNode.create(2, 0, "folder1", 0.0);
		folder = DirectoryNode.create(3, 2, "folder2", 0.0);
		file = FileNode.create(4, 3, "file1", 20.0, "Secret", 42.0);
		file2 = FileNode.create(5, 3, "file3", 20.0, "Top Secret", 42.0);
		root.addChild(folder);
		folder.addChild(file);
		folder.addChild(file2);
    }
	
	@Test
	public void nodeToStringTest() {
		String expDir = "name = folder2, type = Directory, size = 0";
		String expFile = "name = file1, type = File, size = 20, classification = Secret, checksum = 42";
		
		assertEquals(expFile, file.toString());
		assertEquals(expDir, folder.toString());
		
	}
	@Test
	public void buildTreeSuccess() {
		assertEquals("name = folder1, type = Directory, size = 20\n"
				+ " name = folder2, type = Directory, size = 20\n"
				+ "  name = file1, type = File, size = 20, classification = Secret, checksum = 42\n"
				+ "  name = file3, type = File, size = 20, classification = Top Secret, checksum = 42", DirectoryManager.printTree(root));
	}
	
	@Test
	public void filterTopSecretFilesSuccess() {
		String result = DirectoryManager.filterTopSecretFiles(root);
		assertEquals("name = file3, type = File, size = 20, classification = Top Secret, checksum = 42",result);
	}
	
	@Test
	public void filterSecretFilesSuccess() {
		String result = DirectoryManager.filterSecretFiles(root);
		assertEquals("name = file1, type = File, size = 20, classification = Secret, checksum = 42",result);
	}
	
	@Test
	public void filterTopSecretOrSecretFilesSuccess() {
		String result = DirectoryManager.filterSecretOrTopSecretFiles(root);
		assertEquals("name = file1, type = File, size = 20, classification = Secret, checksum = 42\n"
				+ "name = file3, type = File, size = 20, classification = Top Secret, checksum = 42",result);
	}
	
}
