package directorystructure.domainmodel;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeTest {

	DirectoryNode root;
	FileNode file;

	@BeforeEach
	void setUp() {
		root = DirectoryNode.create(2, 0, "folder2", 0.0);
		file = FileNode.create(4, 2, "file4", 40.0, "secret", 42.0);
	}

	@Test
	public void createWithInvalidId() {
		// The file must have an id
		assertThrows(IllegalArgumentException.class, () -> {
			FileNode.create(-1, 2, "file4", 40.0, "secret", 42.0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			DirectoryNode.create(-3, 2, "folder2", 0.0);
		});
	}

	@Test
	public void createWithInvalidName() {
		// The file name can not be empty
		assertThrows(IllegalArgumentException.class, () -> {
			FileNode.create(4, 3, "", 40.0, "secret", 42.0);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			DirectoryNode.create(2, 0, "", 0.0);
		});

		// The file name must start with a letter and contains only letters and numbers
		assertThrows(IllegalArgumentException.class, () -> {
			FileNode.create(3, 2, "@file", 40.0, "secret", 42.0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			DirectoryNode.create(2, 0, "43", 0.0);
		});

	}

	@Test
	public void createWithInvalidParent() {

		// The file must have a parent
		assertThrows(IllegalArgumentException.class, () -> {
			FileNode.create(4, 0, "", 40.0, "secret", 42.0);
		});

	}

	@Test
	public void createWithInvalidSize() {
		// The file name can not be empty
		assertThrows(IllegalArgumentException.class, () -> {
			FileNode.create(4, 2, "file4", -50.0, "secret", 42.0);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			DirectoryNode.create(2, 0, "folder2", -6.0);
		});
	}

}
