package directorystructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.DirectoryStructure;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;
import directorystructure.exceptions.StructureExceptions;
import directorystructure.exceptions.ValidationExceptions;

public class DirectoryManagerTest {

	DirectoryNode root;
	DirectoryNode folder;
	FileNode file, file2;
	List<Node> nodes = new ArrayList<>();
	DirectoryStructure structure = null;

	@BeforeEach
	void setUp() {
		root = DirectoryNode.create(2, 0, "folder1", 0.0);
		folder = DirectoryNode.create(3, 2, "folder2", 0.0);
		file = FileNode.create(4, 3, "file1", 20.0, "Secret", 42.0);
		file2 = FileNode.create(5, 3, "file3", 20.0, "Top Secret", 42.0);
		nodes.add(root);
		nodes.add(folder);
		nodes.add(file);
		nodes.add(file2);
		structure = DirectoryStructure.build(nodes);
	}

	@Test
	public void nodeToStringTest() {
		String expDir = "name = folder2, type = Directory, size = 40";
		String expFile = "name = file1, type = File, size = 20, classification = Secret, checksum = 42";

		assertEquals(expFile, file.toString());
		assertEquals(expDir, folder.toString());

	}

	@Test
	public void buildTreeSuccess() {
		assertEquals(
				"name = folder1, type = Directory, size = 40\n" + " name = folder2, type = Directory, size = 40\n"
						+ "  name = file1, type = File, size = 20, classification = Secret, checksum = 42\n"
						+ "  name = file3, type = File, size = 20, classification = Top Secret, checksum = 42",
				DirectoryManager.buildTree(structure));
	}

	@Test
	public void filterTopSecretFilesSuccess() {
		String result = DirectoryManager.filterTopSecretFiles(structure);
		assertEquals("name = file3, type = File, size = 20, classification = Top Secret, checksum = 42", result);
	}

	@Test
	public void filterSecretFilesSuccess() {
		String result = DirectoryManager.filterSecretFiles(structure);
		assertEquals("name = file1, type = File, size = 20, classification = Secret, checksum = 42", result);
	}

	@Test
	public void filterTopSecretOrSecretFilesSuccess() {
		String result = DirectoryManager.filterSecretOrTopSecretFiles(structure);
		assertEquals("name = file1, type = File, size = 20, classification = Secret, checksum = 42\n"
				+ "name = file3, type = File, size = 20, classification = Top Secret, checksum = 42", result);
	}

	@Test
	public void getPublicFilesSizeSuccess() {
		root.addChild(FileNode.create(6, 3, "file6", 10.0, "Public", 42.0));
		root.addChild(FileNode.create(7, 2, "file7", 15.0, "Public", 42.0));
		Double result = DirectoryManager.getPublicFilesSize(structure);
		assertEquals(25, result);
	}

	@Test
	public void getNonPublicFilesTest() {

		String result = DirectoryManager.getNonPublicFiles(structure, "folder2");
		assertEquals("name = file1, type = File, size = 20, classification = Secret, checksum = 42\n"
				+ "name = file3, type = File, size = 20, classification = Top Secret, checksum = 42", result);
	}

	@Test
	public void getNonPublicFilesNoSuchFolderTest() {

		assertThrows(ValidationExceptions.NoSuchFolderException.class, () -> {
			DirectoryManager.getNonPublicFiles(structure, "folder3");
		});
	}

	@Test
	public void getNonPublicFilesInvalidFolderTest() {

		assertThrows(ValidationExceptions.NoSuchFolderException.class, () -> {
			DirectoryManager.getNonPublicFiles(structure, "file1");
		});
	}
}
