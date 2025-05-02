package directorystructure.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import directorystructure.domainmodel.DirectoryNode;
import directorystructure.domainmodel.FileNode;
import directorystructure.domainmodel.Node;

public class CsvParser implements Parser {

	private static CsvParser INSTANCE = new CsvParser();
	private String delimiter = ";";

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	private CsvParser() {
	}

	public static CsvParser getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Node> parse(InputStream input) throws Exception {

		List<String[]> rawRows = new ArrayList<>(); // lines of input separated by delimiter
		List<Node> nodes = new ArrayList(); // keep track of node type with id

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			String line;

			while ((line = reader.readLine()) != null) {

				if (line.isEmpty() || line.startsWith("#")) {
					continue; // Skip empty or comment lines
				}

				String[] parts = line.split(delimiter);
				rawRows.add(parts);
			}
		}

		for (String[] properties : rawRows) {
			int id;
			int parentId;
			try {
					id = Integer.parseInt(properties[0].trim());
					parentId = ( !properties[1].trim().isEmpty() ? Integer.parseInt(properties[1].trim()) : 0);
			} catch (NumberFormatException e) {
			    throw new IllegalArgumentException("Invalid ID or parentId", e);
			}
			
			String name = properties[2].trim();
			String type = properties[3].trim();
			Double size = (properties.length > 4 && !properties[4].trim().isEmpty()) ? Double.parseDouble(properties[4].trim()) : null;
			String classification = properties.length > 5 && !properties[5].trim().isEmpty() ? properties[5].trim()
					: null;
			Double checksum = properties.length > 6 && !properties[6].trim().isEmpty() ? Double.parseDouble(properties[6].trim()) : null;

			if (type.equalsIgnoreCase("file")) {
				nodes.add(FileNode.create(id, parentId, name, size, classification, checksum));
			}

			else if (type.equalsIgnoreCase("directory")) {
				nodes.add(DirectoryNode.create(id, parentId, name, size));
			} 
			else {
				throw new IllegalArgumentException("Unknown type: " + type);
			}
		}
		return nodes;
	}


}

