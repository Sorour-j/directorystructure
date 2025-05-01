package directorystructure.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;

public class ParserTest {

	@Test
	public void parseSuccess() throws Exception {
		CsvParser parser = CsvParser.getInstance();
		String csv = "1;3;file1;file;10;Secret;42;\n"
					 + "2;;folder2;directory;;;;\n";

		InputStream stream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
		List<Node> nodes = parser.parse(stream);

		assertEquals(2, nodes.size());
		FileNode node = (FileNode) nodes.get(0);

		assertEquals(node.getId(), "1");
		assertEquals(node.getParentId(), "3");
		assertEquals(node.getName(), "file1");
		assertTrue(node instanceof FileNode);
		assertEquals(node.getSize(), 10.0);
		assertEquals(node.getClassification(), "Secret");
		assertEquals(node.getChecksum(), 42.0);
		
		DirectoryNode dir = (DirectoryNode) nodes.get(1);

		assertEquals(dir.getId(), "2");
		assertEquals(dir.getParentId(), "");
		assertEquals(dir.getName(), "folder2");
		assertTrue(dir instanceof DirectoryNode);
		assertEquals(dir.getSize(), 0.0);
	}
}
