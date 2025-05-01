package directorystructure.domainmodel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class FileNodeTest {
	
	 DirectoryNode root; 
	
	@BeforeEach
    void setUp() {
		root = DirectoryNode.create("2", "",null, "folder2", 0.0);
    }

	@Test
	public void createFileNodeSuccess() {
		
		FileNode file = FileNode.create("4", "2",root, "file4", 40.0, "secret", 42.0);
		
		assertEquals("4", file.getId());
		assertEquals("2", file.getParentId());
		assertEquals(root, file.getParent());
		assertEquals("file4", file.getName());
		assertEquals(40.0, file.getSize());
		assertEquals("secret", file.getClassification());
		assertEquals(42.0, file.getChecksum());
		assertFalse(file.isDirectory());
	}
}
