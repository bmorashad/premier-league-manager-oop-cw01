package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOperation {
	public void serialize(String path, Object obj) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(obj);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	public Object deserialize(String path) {
		File objPath = new File(path);

		Object obj = null;
		if (objPath.exists() && objPath.isFile()) {
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
				obj = ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

}
