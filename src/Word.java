import java.util.ArrayList;

public class Word {
	String locate;
	String fileName;
	String word;
	ArrayList<String> FilesAndLocate = new ArrayList<String>();

	public Word() {

	}

	public Word(String locate, String fileName, String word) {
		super();
		this.locate = locate;
		this.fileName = fileName;
		FilesAndLocate.add(fileName + " " + locate);
		this.word = word;
	}

	public String totalFilesAndLocates(ArrayList<String> list) {
		String x = "";
		for (int i = 0; i < FilesAndLocate.size(); i++) {
			if (!x.equals(""))
				x = x + " " + FilesAndLocate.get(i);
			else
				x = x + FilesAndLocate.get(i);
		}
		return x;
	}

	public String toString() {
		String x = "";
		for (int i = 0; i < FilesAndLocate.size(); i++) {
			if (!x.equals(""))
				x = x + " " + FilesAndLocate.get(i);
			else
				x = x + FilesAndLocate.get(i);
		}
		return String.format("%s %s", x, word);
	}
}
