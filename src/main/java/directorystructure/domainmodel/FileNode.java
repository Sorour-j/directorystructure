package directorystructure.domainmodel;

import directorystructure.exceptions.StructureExceptions;

public class FileNode extends Node{

	private final String classification;
	private final Double checksum;
	
	private FileNode(int id, int parentId, String name, Double size, String classification, Double checksum  ) {
		super(id, parentId, name, size);
		this.classification = classification;
		this.checksum = checksum;
	}
	
	public static FileNode create(int id, int parentId, String name, Double size, String classification, Double checksum) {
		
		Node.validaName(name);
		Node.validaId(id);
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
		return String.format("name = %s, type = File, size = %.0f, classification = %s, checksum = %.0f", this.getName(), this.getSize(), this.getClassification(), this.getChecksum());
	}
}
