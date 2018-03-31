import java.io.File;
import java.util.ArrayList;

public class Path {
	public ArrayList<File> PathOperation(String pathAddress) {
		File path = new File(pathAddress);
		File[] list = path.listFiles();
		ArrayList<File> files = new ArrayList<File>();
		for (int i = 0; i < list.length; i++) {
			if (list[i].isFile()) {
				files.add(list[i]);
			} else {
				files.addAll(PathOperation(list[i].getPath()));
			}
		}
		return files;
	}
}
