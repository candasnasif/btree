import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Files {
	public ArrayList<Word> FileOperations(ArrayList<File> files, String stopWords) throws IOException {
		ArrayList<Word> AllWords = new ArrayList<Word>();
		Collections.sort(files);
		for (int i = 0; i < files.size(); i++) {
			AllWords.addAll(ReadFile(files.get(i)));
		}
		DeleteStopWords(AllWords, stopWords);
		return AllWords;
	}

	public ArrayList<Word> ReadFile(File file) throws IOException {
		ArrayList<Word> list = new ArrayList<Word>();
		BufferedReader bf = new BufferedReader(new FileReader(file.getPath()));
		String line;
		int i = 1;
		while ((line = bf.readLine()) != null) {
			String[] Parts = line.split(" ");
			for (int j = 0; j < Parts.length; j++) {
				String word = Parts[j];
				if (word.contains(".") || word.contains(",") || word.contains(")") || word.contains(":")) {
					word = word.substring(0, word.length() - 1);
				}
				if (word.contains("(")) {
					word = word.substring(1, word.length());
				}
				String locate = Integer.toString(line.indexOf(Parts[j]) + 1);
				list.add(new Word(Integer.toString(i) + ":" + locate, file.getName(), word));
			}
			i++;
		}
		return list;
	}

	public void DeleteStopWords(ArrayList<Word> Words, String stopWords) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(stopWords));
		String line;
		while ((line = br.readLine()) != null) {
			for (int i = 0; i < Words.size(); i++) {
				String UpperWord;
				char Upper = Character.toUpperCase(line.charAt(0));
				if (line.length() > 1) {
					UpperWord = line.substring(1);
					UpperWord = Upper + UpperWord;
				} else
					UpperWord = String.valueOf(Upper);
				if (line.equals(Words.get(i).word) || UpperWord.equals(Words.get(i).word)
						|| Words.get(i).word.contains("..") || Words.get(i).word.equals(",")
						|| Words.get(i).word.equals("-") || Words.get(i).word.equals("(")
						|| Words.get(i).word.equals(")") || Words.get(i).word.equals(".")
						|| Words.get(i).word.equals("\"")) {
					Words.remove(i);
				}
			}
		}
	}

	public void SameWords(ArrayList<Word> x) {
		for (int i = 0; i < x.size(); i++) {
			for (int j = 0; j < x.size(); j++) {
				if (j != i) {
					if (x.get(i).word.equals(x.get(j).word)) {
						int control = 0;
						for (int j2 = 0; j2 < x.get(i).FilesAndLocate.size(); j2++) {
							if (x.get(i).FilesAndLocate.get(j2).contains(x.get(j).fileName)) {
								control = 1;
								String same = x.get(i).FilesAndLocate.get(j2);
								same = x.get(i).FilesAndLocate.get(j2) + " " + x.get(j).locate;
								x.get(i).FilesAndLocate.remove(j2);
								x.get(i).FilesAndLocate.add(j2, same);
								x.remove(j);
								break;
							}
						}
						if (control == 0) {
							String Parts1 = x.get(j).fileName;
							String[] Parts2 = x.get(i).FilesAndLocate.get(x.get(i).FilesAndLocate.size() - 1)
									.split(" ");
							if (Parts1.compareTo(Parts2[0]) < 0) {
								x.get(i).FilesAndLocate.add(x.get(i).FilesAndLocate.size() - 1,
										x.get(j).fileName + " " + x.get(j).locate);
							} else
								x.get(i).FilesAndLocate.add(x.get(j).fileName + " " + x.get(j).locate);
							x.remove(j);
						}
					}
				}
			}
		}

	}

	public int CompareLocate(String a, String b) {
		String[] aSplit = a.split(":");
		String[] bSplit = b.split(":");
		int control = 0;
		if (aSplit[0].toLowerCase().compareTo(bSplit[0].toLowerCase()) < 0) {
			if (aSplit[1].compareTo(bSplit[1]) < 0) {
				control = 0;
			} else {
				control = 1;
			}
		} else {
			control = 1;
		}
		return control;
	}
}
