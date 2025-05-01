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
		
		Node.validaName(name);
		Node.validaId(id);
		Node.validateSize(size);
		
		if (parentId.isEmpty() || parentId == null || parent == null) {
	        throw new IllegalArgumentException("A file must be in a directory");
		}
		
		Node.validateParent(parent);
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
