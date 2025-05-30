package directorystructure.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.DirectoryStructure;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;
import directorystructure.exceptions.ValidationExceptions;

public class ParserTest {

	@Test
	public void parseSuccess() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;2;file1;file;10;Secret;42;\n"
					 + "2;;folder2;directory;;;;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);

		assertEquals(2, nodes.size());
		FileNode node = (FileNode) nodes.get(0);

		assertEquals(node.getId(), 1);
		assertEquals(node.getParentId(), 2);
		assertEquals(node.getName(), "file1");
		assertTrue(node instanceof FileNode);
		assertEquals(node.getSize(), 10.0);
		assertEquals(node.getClassification(), "Secret");
		assertEquals(node.getChecksum(), 42.0);
		
		DirectoryNode dir = (DirectoryNode) nodes.get(1);

		assertEquals(dir.getId(), 2);
		assertEquals(dir.getParentId(), 0);
		assertEquals(dir.getName(), "folder2");
		assertTrue(dir instanceof DirectoryNode);
		assertEquals(dir.getSize(), 0.0);
	}
	
	@Test
	public void IvalidIdTest() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "A;2;file1;file;10;Confidential;42;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, ()->{
				 parser.parse(stream);
		});
				
	}
	
	@Test
	public void IvalidTypeTest() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "A;2;file1;Picture;10;Confidential;42;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, ()->{
				 parser.parse(stream);
		});
				
	}
	
	@Test
	public void IvalidClassificationTest() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;2;file1;file;10;Confidential;42;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, ()->{
				 parser.parse(stream);
		});
				
	}
	
	
	
}
