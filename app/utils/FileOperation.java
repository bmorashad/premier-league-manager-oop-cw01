package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOperation {
	private String path;
	public FileOperation(String path) {
		this.path = path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void write(String content) {
		File file = new File(path);
		try (FileWriter fw = new FileWriter(file)) {
			fw.append(content);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public void append(String content) {
		File file = new File(path);
		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
			pw.print(content);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
