package directorystructure.domainmodel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectoryNodeTest {
	
	@Test
	public void createDirectoryNodeSuccess(){
		DirectoryNode root = DirectoryNode.create("2", "", null, "folder2", 0.0);
		
		assertEquals("2", root.getId());
		assertEquals("", root.getParentId());
		assertEquals("folder2", root.getName());
		assertEquals(0.0, root.getSize());	
		assertTrue(root.isDirectory());
	}
	
	@Test
	public void createWithInvalidId(){
		// A directory must have an id
		 assertThrows(IllegalArgumentException.class, () -> {
			 DirectoryNode.create("", "",null, "folder2", 0.0);
		 });
	}
	
	@Test
	public void createWithInvalidName(){
		// A directory' name can not be empty
		 assertThrows(IllegalArgumentException.class, () -> {
			 DirectoryNode.create("2", "", null, "", 0.0);
		 });
	}
	
	@Test
	public void createWithInvalidParent(){
		// Parent of a directory must only be a directory
		DirectoryNode root = DirectoryNode.create("2", "", null, "folder2", 0.0);
		FileNode file = FileNode.create("4", "2", root, "file4", 40.0, "secret", 42.0);
		
		 assertThrows(IllegalArgumentException.class, () -> {
			 DirectoryNode.create("2", "", file, "", 0.0);
		 });
	}
}
