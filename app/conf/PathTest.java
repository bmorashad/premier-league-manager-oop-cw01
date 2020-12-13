package conf;
public class PathTest {
	public static void main(String[] args) {
		String projectRoot = PathConfiguration.projectRoot;
		System.out.println("initial " + projectRoot);

		projectRoot = projectRoot.replace("/", "\\\\");
		System.out.println("Windows Double backslash" + projectRoot);

		projectRoot = projectRoot.replace("\\\\", "/");
		projectRoot = projectRoot.replace("\\", "/");
		System.out.println("Linux " + projectRoot);

		projectRoot = projectRoot.replace("/", "\\");
		System.out.println("Windows Single backslash " + projectRoot);

		projectRoot = projectRoot.replace("\\\\", "/");
		projectRoot = projectRoot.replace("\\", "/");
		System.out.println("Linux " + projectRoot);
	}
}
