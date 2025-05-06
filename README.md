## Directory Structure Manager

This project implements a parser and query utilities for a directory structure represented in a CSV file. It supports building a domain model, generating indented tree views, classifying files based on sensitivity, and performing various queries.

### Overview
This project provides a robust way to model file systems as tree structures in Java. It includes features for:

* Parsing the CSV files and build a directory structures
* Building, validating and displaying tree structures
* Working with classified files (Secret, Top Secret, Public)
* Calculating directory sizes
* Filtering files by classification
#### CSV Format
The library expects CSV files with the following format:

id;parentId;name;type;size;classification;checksum

Example:
```csv
1;2;file1;file;10;Secret;42
2;;folder2;directory;;;;
```
Fields:

* id: Unique identifier for the node
* parentId: ID of the parent node (empty for root)
* name: Node name (must start with a letter, contain only letters and numbers)
* type: Either "file" or "directory"
* size: File size (optional for directories)
* classification: File classification level (Secret, Top Secret, Public)
* checksum: File checksum value

### Usage

#### Parsing a CSV and Building a Structure
```java
CsvParser parser = CsvParser.getInstance();
InputStream stream = new FileInputStream("directory.csv");
List<Node> nodes = parser.parse(stream);
DirectoryStructure structure = DirectoryStructure.build(nodes);
```
#### Displaying a Directory Tree
```java
String treeRepresentation = DirectoryManager.buildTree(structure);
System.out.println(treeRepresentation);
```
#### Filtering by Classification
```java

// Get all Secret files
String secretFiles = DirectoryManager.filterSecretFiles(structure);

// Get all Top Secret files
String topSecretFiles = DirectoryManager.filterTopSecretFiles(structure);

// Get all Secret or Top Secret files
String classifiedFiles = DirectoryManager.filterSecretOrTopSecretFiles(structure);
Working with Public Files
java// Get total size of public files
Double publicFilesSize = DirectoryManager.getPublicFilesSize(structure);

// Get non-public files in a specific folder
String nonPublicFiles = DirectoryManager.getNonPublicFiles(structure, "folderName");
```

### Validation and Safety Features
The library includes robust validation:

* Node IDs must be positive
* Node names must start with a letter and contain only alphanumeric characters
* File sizes cannot be negative
* Files must have a parent directory
* Directory structures cannot have cycles
* Multiple root nodes are not allowed
* Parent IDs must reference existing directories

### Project Structure
```
src
├── main/java/directorystructure
│   ├── domainmodel
│   │   ├── DirectoryNode.java      # Directory representation
│   │   ├── DirectoryStructure.java # Tree structure manager
│   │   ├── FileNode.java           # File representation
│   │   └── Node.java               # Abstract base class
│   ├── exceptions
│   │   ├── StructureExceptions.java  # Structure-related exceptions
│   │   └── ValidationExceptions.java # Validation-related exceptions
│   ├── parser
│   │   ├── CSVParser.java          # CSV implementation
│   │   └── Parser.java             # Parser interface
│   └── services
│       └── DirectoryManager.java   # Directory operations
└── test/java/directorystructure
    ├── domainmodel
    │   ├── DirectoryNodeTest.java
    │   ├── DirectoryStructureTest.java
    │   ├── FileNodeTest.java
    │   └── NodeTest.java
    ├── parser
    │   └── ParserTest.java
    └── services
        └── DirectoryManagerTest.java
```

### Requirements

Java 15 or higher (for pattern matching and other features)
JUnit 5 for running tests
