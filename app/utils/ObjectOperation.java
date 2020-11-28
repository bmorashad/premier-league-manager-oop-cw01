package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOperation {
	public void serialize(String path, Object obj) {
		try {
			FileOutputStream fos
				= new FileOutputStream(path);
			ObjectOutputStream oos 
				= new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public Object deserialize(String path) {
		File objPath = new File(path);

		Object obj = null;
		if (objPath.exists() && objPath.isFile()) {
			try {
				FileInputStream fis
					= new FileInputStream(path);
				ObjectInputStream ois
					= new ObjectInputStream(fis);
				obj = ois.readObject();
				ois.close(); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

}
