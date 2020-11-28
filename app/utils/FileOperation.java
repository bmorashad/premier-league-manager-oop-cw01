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
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.append(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public void append(String content) {
		File file = new File(path);
		FileWriter fw;
		BufferedWriter bw;
		PrintWriter pw;
		try {
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.print(content);
			pw.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
