package directorystructure.domainmodel;

import org.junit.jupiter.api.Test;

import directorystructure.exceptions.StructureExceptions;
import directorystructure.exceptions.ValidationExceptions;
import directorystructure.parser.CsvParser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DirectoryStructureTest {

	@Test
	public void buildStructureSuccess() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;2;file1;file;10;Secret;42;\n" + "2;;folder2;directory;;;;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);
		DirectoryStructure structure = DirectoryStructure.build(nodes);
		List<Node> children = structure.getRoot().getChildren();
		assertEquals(1, children.size());
		assertEquals("file1", children.get(0).getName());
		assertEquals(1, children.get(0).getId());
	}

	@Test
	public void duplicateIdTest() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;2;file1;file;10;Secret;42;\n" + "1;;folder2;directory;;;;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);
		assertThrows(IllegalStateException.class, () -> {
			DirectoryStructure.build(nodes);
		});
	}

	@Test
	public void multipleRootTest() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;;folder2;directory;;;;\n" + "2;;folder2;directory;;;;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);
		assertThrows(StructureExceptions.MultipleRootsException.class, () -> {
			DirectoryStructure.build(nodes);
		});
	}

	@Test
	public void invalidParentTest() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;;folder2;directory;;;;\n" + "2;3;folder2;directory;;;;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);
		assertThrows(StructureExceptions.InvalidParentException.class, () -> {
			DirectoryStructure.build(nodes);
		});
	}

	@Test
	public void notDirectoryParentTest() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;2;file1;file;10;Secret;42;\n" + "2;;folder2;directory;;;;\n" + "3;1;folder2;directory;;;;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);
		assertThrows(StructureExceptions.ParentNotDirectoryException.class, () -> {
			DirectoryStructure.build(nodes);
		});
	}

}
