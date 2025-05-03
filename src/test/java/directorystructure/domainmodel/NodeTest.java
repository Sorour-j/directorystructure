package directorystructure.domainmodel;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import directorystructure.exceptions.ValidationExceptions;

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
		// The id must be a positive number
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			FileNode.create(-1, 2, "file4", 40.0, "secret", 42.0);
		});
		// The id must be a positive number
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			DirectoryNode.create(-3, 2, "folder2", 0.0);
		});
	}

	@Test
	public void createWithInvalidName() {
		// Name can not be empty
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			FileNode.create(4, 3, "", 40.0, "secret", 42.0);
		});
		// Name can not be empty
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			DirectoryNode.create(2, 0, "", 0.0);
		});

		// Name must start with a letter and contains only letters and numbers
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			FileNode.create(3, 2, "@file", 40.0, "secret", 42.0);
		});
		// Name must start with a letter and contains only letters and numbers
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			DirectoryNode.create(2, 0, "43", 0.0);
		});

	}

	@Test
	public void createWithInvalidParent() {

		// A file must be in a directory
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			FileNode.create(4, 0, "", 40.0, "secret", 42.0);
		});

	}

	@Test
	public void createWithInvalidSize() {
		// Size can not be a negative number
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			FileNode.create(4, 2, "file4", -50.0, "secret", 42.0);
		});

		// Size can not be a negative number
		assertThrows(ValidationExceptions.InvalidNodeAttributeException.class, () -> {
			DirectoryNode.create(2, 0, "folder2", -6.0);
		});
	}

}
