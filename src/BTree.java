import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class BTree {
	static int max = 4;
	Node root = new Node(null);

	BTree(Node root) {
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}

	public void INSERT(Node x, Word newKey) {
		String word = newKey.word;
		if (x.leaf) {
			int control = 0;
			for (int i = 0; i < x.Keys.size(); i++) {
				if (word.toLowerCase().compareTo(x.Keys.get(i).word.toLowerCase()) < 0) {
					control = 1;
					x.Keys.add(i, newKey);
					break;
				}
			}
			if (control == 0) {
				x.Keys.add(newKey);
			}
			if (x.Keys.size() > 4) {
				Cut(x);
			}
		} else {
			int control = 0;
			for (int i = 0; i < x.Keys.size(); i++) {
				if (word.toLowerCase().compareTo(x.Keys.get(i).word.toLowerCase()) < 0) {
					control = 1;
					INSERT(x.Child.get(i), newKey);
					break;
				}
			}
			if (control == 0) {
				INSERT(x.Child.get(x.Keys.size()), newKey);
			}
		}
	}

	public void Cut(Node x) {
		if (x.parent == null) {
			Node newRoot = new Node(null);
			Node newNode = new Node(newRoot);
			newNode.Keys.add(x.Keys.get(0));
			newNode.Keys.add(x.Keys.get(1));
			newRoot.Keys.add(x.Keys.get(2));
			newNode.next = x;
			if (x.previous != null) {
				x.previous.next = newNode;
			}
			x.previous = newNode;
			x.Keys.remove(2);
			x.Keys.remove(0);
			x.Keys.remove(0);
			newRoot.Child.add(newNode);
			newRoot.Child.add(x);
			newRoot.leaf = false;
			x.parent = newRoot;
			this.root = newRoot;
			if (x.leaf == false) {
				newNode.Child.add(x.Child.get(0));
				newNode.Child.get(0).parent = newNode;
				newNode.Child.add(x.Child.get(1));
				newNode.Child.get(1).parent = newNode;
				newNode.Child.add(x.Child.get(2));
				newNode.Child.get(2).parent = newNode;
				newNode.Child.get(0).next = newNode.Child.get(1);
				newNode.Child.get(1).next = newNode.Child.get(2);
				newNode.Child.get(2).previous = newNode.Child.get(1);
				newNode.Child.get(1).previous = newNode.Child.get(0);
				newNode.leaf = false;
				x.Child.remove(0);
				x.Child.remove(0);
				x.Child.remove(0);
			}
		} else {
			Node newNode = new Node(x.parent);
			int control = 0;
			String word = x.Keys.get(2).word;
			for (int i = 0; i < x.parent.Keys.size(); i++) {
				if (word.toLowerCase().compareTo(x.parent.Keys.get(i).word.toLowerCase()) < 0) {
					control = 1;
					x.parent.Keys.add(i, x.Keys.get(2));
					x.parent.Child.add(i, newNode);
					break;
				}
			}
			if (control == 0) {
				x.parent.Keys.add(x.Keys.get(2));
				x.parent.Child.add(x.parent.Keys.indexOf(x.Keys.get(2)), newNode);
			}
			newNode.Keys.add(x.Keys.get(0));
			newNode.Keys.add(x.Keys.get(1));
			newNode.next = x;
			if (x.previous != null) {
				x.previous.next = newNode;
			}
			x.previous = newNode;
			x.Keys.remove(2);
			x.Keys.remove(0);
			x.Keys.remove(0);
			if (x.leaf == false) {
				newNode.Child.add(x.Child.get(0));
				newNode.Child.get(0).parent = newNode;
				newNode.Child.add(x.Child.get(1));
				newNode.Child.get(1).parent = newNode;
				newNode.Child.add(x.Child.get(2));
				newNode.Child.get(2).parent = newNode;
				newNode.Child.get(0).next = newNode.Child.get(1);
				newNode.Child.get(1).next = newNode.Child.get(2);
				newNode.Child.get(2).previous = newNode.Child.get(1);
				newNode.Child.get(1).previous = newNode.Child.get(0);
				newNode.leaf = false;
				x.Child.remove(0);
				x.Child.remove(0);
				x.Child.remove(0);
			}
			if (x.parent.Keys.size() > 4) {
				Cut(x.parent);
			}
		}
	}


	public void INORDER(Node x, PrintWriter writer) {
		if (x.Child.size() == 0) {
			LevelOrderWrite(x, writer);
			return;
		}
		for (int i = 0; i < x.Child.size(); i++) {
			INORDER(x.Child.get(i), writer);
			if (i != x.Keys.size())
				writer.print(x.Keys.get(i).word + " ");
		}
	}

	public Word LevelOrderSearch(Node x, String y) {
		Node p = x;
		Node q = x;
		Word find = new Word();
		while (true) {
			while (p != null) {
				find = LevelOrderFind(p, y);
				if (find.word != null)
					break;
				p = p.next;
			}
			if (find.word != null)
				break;
			if (q.Child.size() > 0) {
				q = q.Child.get(0);
				p = q;
			} else
				break;
		}
		return find;
	}

	public ArrayList<Word> LevelOrderSearch1(Node x, String y) {
		Node p = x;
		Node q = x;
		ArrayList<Word> find = new ArrayList<Word>();
		ArrayList<Word> control = new ArrayList<Word>();

		while (true) {
			while (p != null) {
				control = LevelOrderFind1(p, y);
				find.addAll(control);
				p = p.next;
			}
			if (q.Child.size() > 0) {
				q = q.Child.get(0);
				p = q;
			} else
				break;
		}
		return find;
	}

	public ArrayList<Word> LevelOrderFind1(Node x, String y) {
		ArrayList<Word> results = new ArrayList<Word>();

		for (int i = 0; i < x.Keys.size(); i++) {
			String a = x.Keys.get(i).word.substring(0, y.length());
			if (a.toLowerCase().contains(y.toLowerCase())) {
				results.add(x.Keys.get(i));
			}
		}
		return results;
	}

	public Word LevelOrderFind(Node x, String y) {
		Word results = new Word();
		for (int i = 0; i < x.Keys.size(); i++) {
			if (x.Keys.get(i).word.toLowerCase().contains(y.toLowerCase())) {
				results = x.Keys.get(i);
				break;
			}
		}
		return results;
	}

	public void LevelOrderWrite(Node x, PrintWriter writer) {
		int i = 0;
		for (i = 0; i < x.Keys.size(); i++) {
			writer.print(x.Keys.get(i).word + " ");
		}
	}

	public void LevelOrder(Node x, PrintWriter writer) {
		Node p = x;
		Node q = x;
		while (true) {
			while (p != null) {
				LevelOrderWrite(p, writer);
				p = p.next;
			}
			if (q.Child.size() > 0) {
				q = q.Child.get(0);
				p = q;
			} else
				break;
		}
		writer.println();

	}

	public void Command(String fileName, String outputFileName, Node root) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(fileName));
		PrintWriter writer = new PrintWriter(outputFileName, "UTF-8");
		String line;
		while ((line = bf.readLine()) != null) {
			if (line.equals("traverse level-order")) {
				writer.println(line);
				LevelOrder(root,writer);
				writer.println();
			}
			else if(line.equals("traverse in-order")){
				writer.println(line);
				INORDER(root, writer);
				writer.println();
				writer.println();	
			}
			else {
				String[] parts = line.split(" ");
				if (parts.length < 3) {
					if (parts[1].contains("*")) {
						writer.println(line);
						String search = parts[1].substring(0, parts[1].length() - 1);
						ArrayList<Word> result = new ArrayList<Word>();
						result = LevelOrderSearch1(root, search);
						QuickSortString(result, 0, result.size() - 1);
						for (int i = 0; i < result.size(); i++) {
							for (int j = 0; j < result.get(i).FilesAndLocate.size(); j++) {
								writer.println(result.get(i).word+" "+result.get(i).FilesAndLocate.get(j));
							}
						}
						writer.println();
					}
					else {
						writer.println(line);
						Word result=LevelOrderSearch(root, parts[1]);
						for (int i = 0; i < result.FilesAndLocate.size(); i++) {
							writer.println(result.FilesAndLocate.get(i));
						}
						writer.println();
					}
				}
				else {
					if(parts[2].equals("and")){
						writer.println(line);
						Word first=LevelOrderSearch(root, parts[1]);
						Word second=LevelOrderSearch(root, parts[3]);
						ArrayList<String> firstWord=new ArrayList<String>();
						ArrayList<String> secondWord=new ArrayList<String>();
						for (int i = 0; i < first.FilesAndLocate.size(); i++) {
							String[] part=first.FilesAndLocate.get(i).split(" ");
							for (int j = 0; j <second.FilesAndLocate.size(); j++) {
								if(second.FilesAndLocate.get(j).contains(part[0])){
									firstWord.add(first.word+" "+first.FilesAndLocate.get(i));
									secondWord.add(second.word+" "+second.FilesAndLocate.get(j));
								}
							}
						}
						for (int i = 0; i < firstWord.size(); i++) {
							writer.println(firstWord.get(i));

						}
						for (int i = 0; i < secondWord.size(); i++) {
							writer.println(secondWord.get(i));

						}
						writer.println();
					}
					else {
						writer.println(line);
						Word first=LevelOrderSearch(root, parts[1]);
						Word second=LevelOrderSearch(root, parts[3]);
						ArrayList<String> firstWord=new ArrayList<String>();
						ArrayList<String> secondWord=new ArrayList<String>();
						for (int i = 0; i < first.FilesAndLocate.size(); i++) {
							String[] part=first.FilesAndLocate.get(i).split(" ");
							for (int j = 0; j <second.FilesAndLocate.size(); j++) {
								if(!second.FilesAndLocate.get(j).contains(part[0])){
									firstWord.add(first.word+" "+first.FilesAndLocate.get(i));
								}
							}
						}
						for (int i = 0; i < firstWord.size(); i++) {
							writer.println(firstWord.get(i));

						}
						
						writer.println();
					}
				}
			}
		}
		writer.close();
	}

	public ArrayList<Word> QuickSortString(ArrayList<Word> a, int p,
			int r) {
		int q;
		if (p < r) {
			q = PartitionString(a, p, r);
			QuickSortString(a, p, q - 1);
			QuickSortString(a, q + 1, r);
		}
		return a;
	}

	public int PartitionString(ArrayList<Word> a, int p, int r) {

		String x = a.get(r).word;
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			if (a.get(j).word.compareTo(x) <= 0) {
				i = i + 1;

				Swap(a.get(i), a.get(j));
			}
		}
		Swap(a.get(i + 1), a.get(r));
		return i + 1;
	}

	public void Swap(Word a, Word b) {
		Word temp = new Word();
		temp.word = a.word;
		temp.locate = a.locate;
		temp.fileName = a.fileName;
		temp.FilesAndLocate = a.FilesAndLocate;
		a.word = b.word;
		a.fileName = b.fileName;
		a.locate = b.locate;
		a.FilesAndLocate = b.FilesAndLocate;
		b.word = temp.word;
		b.fileName = temp.fileName;
		b.locate = temp.locate;
		b.FilesAndLocate = temp.FilesAndLocate;
	}

}
