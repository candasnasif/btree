import java.util.ArrayList;

public class Node {
	
	ArrayList<Word> Keys = new ArrayList<Word>();
	ArrayList<Node> Child = new ArrayList<Node>();
	Node parent;
	Node next = null;
	Node previous = null;
	boolean leaf;
	Node(Node parent){
		this.parent = parent;
		this.leaf = true;
	}

}
