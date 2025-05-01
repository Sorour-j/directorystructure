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
	
	@Test
	public void createWithInvalidId(){
		// The file must have an id
		 assertThrows(IllegalArgumentException.class, () -> {
			 FileNode.create("", "2",root, "file4", 40.0, "secret", 42.0);
		 });
	}
	
	@Test
	public void createWithInvalidName(){
		// The file name can not be empty
		 assertThrows(IllegalArgumentException.class, () -> {
			 FileNode.create("", "2",root, "", 40.0, "secret", 42.0);
		 });
		 
		// The file name must start with a letter and contains only letters and numbers  
		assertThrows(IllegalArgumentException.class, () -> {
			 FileNode.create("23", "2",root, "", 40.0, "secret", 42.0);
				 });
	}
	
	@Test
	public void createWithInvalidParent(){
		
		FileNode file = FileNode.create("3", "2",root, "file3", 40.0, "secret", 42.0);
		
		// The file must have a parent
		 assertThrows(IllegalArgumentException.class, () -> {
			 FileNode.create("4", "",null, "", 40.0, "secret", 42.0);
		 });
		 
		 // Just a directory node can be a parent of file node
		 assertThrows(IllegalArgumentException.class, () -> {
			 FileNode.create("4", "3",file, "", 40.0, "secret", 42.0);
		 });
	}
	
	@Test
	public void createWithInvalidSize(){
		// The file name can not be empty
		 assertThrows(IllegalArgumentException.class, () -> {
			 FileNode.create("4", "2",root, "file4", -50.0, "secret", 42.0);
		 });
	}
}
