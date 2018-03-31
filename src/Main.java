import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		Path p = new Path();
		String commands = args[2];
		String output = args[3];
		String stopWords = args[1];
		Node root = new Node(null);
		BTree tree = new BTree(root);
		Files words = new Files();
		java.nio.file.Path c = FileSystems.getDefault().getPath(args[0]);
		ArrayList<File> files = new ArrayList<File>();
		files = p.PathOperation(c.toString());
		ArrayList<Word> AllWords = new ArrayList<Word>();
		AllWords = words.FileOperations(files, stopWords);
		words.SameWords(AllWords);
		for (int i = 0; i < AllWords.size(); i++) {
			tree.INSERT(tree.root, AllWords.get(i));
		}
		Node control = new Node(null);
		control = tree.getRoot();

		tree.Command(commands, output, control);
	}

}
