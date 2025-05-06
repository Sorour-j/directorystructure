package directorystructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
import directorystructure.parser.CsvParser;

public class DirectoryManagerTest {

	DirectoryNode root;
	DirectoryNode folder;
	
	DirectoryStructure structure = null;
	
	@BeforeEach
	void setUp() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;;folder1;directory;;;;\n" + 
					 "2;1;folder2;directory;;;;\n" +
					 "3;2;file1;file;20;Secret;42;\n" +
					 "4;2;file3;file;20;Top secret;42;\n" +
					 "5;2;file6;file;10;Public;42;\n" +
					 "6;2;file7;file;15;Public;42;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);
		structure = DirectoryStructure.build(nodes);
		root = structure.getRoot();
	}

	@Test
	public void nodeToStringTest() {
		String expDir = "name = folder1, type = Directory, size = 65";
		String expFile = "name = file1, type = File, size = 20, classification = Secret, checksum = 42";

		assertEquals(expFile, root.getFiles().get(0).toString());
		assertEquals(expDir, root.toString());
	}

	@Test
	public void buildTreeSuccess() {
		assertEquals(
				"name = folder1, type = Directory, size = 65\n" + 
				" name = folder2, type = Directory, size = 65\n"+ 
				"  name = file1, type = File, size = 20, classification = Secret, checksum = 42\n" + 
				"  name = file3, type = File, size = 20, classification = Top secret, checksum = 42\n" +
				"  name = file6, type = File, size = 10, classification = Public, checksum = 42\n" +
				"  name = file7, type = File, size = 15, classification = Public, checksum = 42",
				DirectoryManager.buildTree(structure));
	}

	@Test
	public void filterTopSecretFilesSuccess() {
		String result = DirectoryManager.filterTopSecretFiles(structure);
		assertEquals("name = file3, type = File, size = 20, classification = Top secret, checksum = 42", result);
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
				+ "name = file3, type = File, size = 20, classification = Top secret, checksum = 42", result);
	}

	@Test
	public void getPublicFilesSizeSuccess() {
		
		Double result = DirectoryManager.getPublicFilesSize(structure);
		assertEquals(25, result);
	}

	@Test
	public void getNonPublicFilesTest() {

		String result = DirectoryManager.getNonPublicFiles(structure, "folder2");
		assertEquals("name = file1, type = File, size = 20, classification = Secret, checksum = 42\n"
				+ "name = file3, type = File, size = 20, classification = Top secret, checksum = 42", result);
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
