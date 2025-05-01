package directorystructure.parser;

import java.io.InputStream;
import java.util.List;

import directorystructure.domainmodel.Node;

public interface Parser {
	  List<Node> parse(InputStream input) throws Exception;
}
