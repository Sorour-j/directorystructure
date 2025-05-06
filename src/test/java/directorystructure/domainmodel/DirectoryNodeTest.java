package directorystructure.domainmodel;

import org.junit.jupiter.api.Test;

import directorystructure.exceptions.ValidationExceptions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class DirectoryNodeTest {
	
	@Test
	public void createDirectoryNodeSuccess(){
		DirectoryNode root = DirectoryNode.create(1, 0, "folder2", 0.0);
		
		assertEquals(1, root.getId());
		assertEquals(0, root.getParentId());
		assertEquals("folder2", root.getName());
		assertEquals(0.0, root.getSize());	
		assertTrue(root.isDirectory());
	}
	@Test
	public void addInvalidChildNodeTest(){
		DirectoryNode root = DirectoryNode.create(1, 0, "folder2", 0.0);
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, ()->{
			root.addChild(null);
		});
		
	}
	
	@Test
	public void calculateSizeTest(){
		DirectoryNode root = DirectoryNode.create(1, 0, "folder2", 0.0);
		root.addChild(FileNode.create(2, 1, "file1", 15.0, "Secret", 42.0));
		assertEquals(root.getSize(), 15.0);
		
	}
	
	@Test
	public void getFilesTest(){
		DirectoryNode root = DirectoryNode.create(1, 0, "folder2", 0.0);
		FileNode node = FileNode.create(2, 1, "file1", 15.0, "Secret", 42.0);
		root.addChild(node);
		root.addChild(DirectoryNode.create(3, 1, "folder2", 0.0));
		List<FileNode> files = new ArrayList<>();
		files.add(node);
		assertEquals(root.getFiles(), files);
		
	}
	
	@Test
	public void getFolderTest(){
		DirectoryNode root = DirectoryNode.create(1, 0, "folder1", 0.0);
		DirectoryNode node = DirectoryNode.create(2, 1, "folder2", 0.0);
		root.addChild(node);
		root.addChild(FileNode.create(3, 1, "file1", 15.0, "Secret", 42.0));
		List<DirectoryNode> folders = new ArrayList<>();
		folders.add(node);
		assertEquals(root.getFolders(), folders);
		
	}
}
