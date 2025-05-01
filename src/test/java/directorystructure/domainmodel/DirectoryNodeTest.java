package directorystructure.domainmodel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectoryNodeTest {
	
	@Test
	public void createDirectoryNodeSuccess(){
		DirectoryNode root = DirectoryNode.create("2", "", "folder2", 0.0);
		
		assertEquals("2", root.getId());
		assertEquals("", root.getParentId());
		assertEquals("folder2", root.getName());
		assertEquals(0.0, root.getSize());	
		assertTrue(root.isDirectory());
	}
}
