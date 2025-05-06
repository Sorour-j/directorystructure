
package directorystructure.domainmodel;

import directorystructure.exceptions.StructureExceptions;

/**
 * Represents a file node in a directory structure. A file has additional
 * properties like classification and checksum.
 * 
 * @author Sorour
 * @version 1.0
 */

public class FileNode extends Node {

	private final String classification;
	private final Double checksum;

	/**
	 * Constructs a new FileNode with the specified properties.
	 * 
	 * @param id             The unique identifier
	 * @param parentId       The parent identifier
	 * @param name           The name
	 * @param size           The size in bytes
	 * @param classification The security classification
	 * @param checksum       The checksum value
	 */

	private FileNode(int id, int parentId, String name, Double size, String classification, Double checksum) {
		super(id, parentId, name, size);
		this.classification = classification;
		this.checksum = checksum;
	}

	/**
	 * Factory method to create a new FileNode.
	 * 
	 * @param id             The unique identifier
	 * @param parentId       The parent identifier
	 * @param name           The name
	 * @param size           The size in bytes
	 * @param classification The security classification
	 * @param checksum       The checksum value
	 * @return A new FileNode instance
	 * @throws IllegalArgumentException If any parameters are invalid
	 */

	public static FileNode create(int id, int parentId, String name, Double size, String classification,
			Double checksum) {

		Node.validateName(name);
		Node.validateId(id);
		Node.validateSize(size);

		if (parentId == 0) {
			throw new StructureExceptions.ParentNotDirectoryException("The file must be in a directory");
		}

		return new FileNode(id, parentId, name, size, classification, checksum);
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	public String getClassification() {
		return classification;
	}

	public Double getChecksum() {
		return checksum;
	}

	@Override
	public String toString() {
		return String.format("name = %s, type = File, size = %.0f, classification = %s, checksum = %.0f",
				this.getName(), this.getSize(), this.getClassification(), this.getChecksum());
	}

	@Override
	public Double getSize() {
		return (this.size != null) ? this.size : 0.0;
	}
}
