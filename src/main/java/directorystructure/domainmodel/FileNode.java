package directorystructure.domainmodel;

public class FileNode extends Node{

	private final String classification;
	private final Double checksum;
	
	private FileNode(String id, String parentId, Node parent, String name, Double size, String classification, Double checksum  ) {
		super(id, parentId, parent, name, size);
		this.classification = classification;
		this.checksum = checksum;
	}
	
	public static FileNode create(String id, String parentId, Node parent, String name, Double size, String classification, Double checksum) {
		
		if (id.isEmpty() || id == null) {
	        throw new IllegalArgumentException("Id cannot be empty");
		}
		
		if (parentId.isEmpty() || parentId == null || parent == null) {
	        throw new IllegalArgumentException("A file must be in a directory");
		}
		
		if (parent != null && ! parent.isDirectory()) {
			throw new IllegalArgumentException("Parent of a directory must be another directory ");
		}
		
		if (name == null || name.isEmpty()) {
	        throw new IllegalArgumentException("Directory name cannot be empty");
		}
		
		if (!name.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
			throw new IllegalArgumentException("File name must only contain alphabetic characters and numbers.");
		}
		
		if (size < 0) {
	        throw new IllegalArgumentException("Size of file can not be a negative number");
		}
		return new FileNode(id, parentId, parent, name, size, classification, checksum);
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
}
