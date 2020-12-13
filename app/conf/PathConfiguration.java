package conf;

import java.util.Arrays;

public class PathConfiguration {
	public static final String projectName = "premier-league"; 
	public static final String projectRoot; 
	public static final String dataPath;
	public static final String updatesPath; 
	public static final String cliUpdatePath; 
	public static final String guiUpdatesPath; 
	public static final String activeSeasonPath;

	static {
		String pathSeparator = "/";
		String root = System.getProperty("user.dir");
		root = root.replace("\\\\", pathSeparator);
		root = root.replace("\\", pathSeparator);
		String[] folders = root.split(pathSeparator);
		int indexOfProjectRootFolder = Arrays.asList(folders).lastIndexOf(projectName);
		projectRoot = String.join(pathSeparator, Arrays.copyOfRange(folders, 0, indexOfProjectRootFolder + 1)) + pathSeparator;
		dataPath = projectRoot + "app/.data/";
		updatesPath = dataPath + ".updates/"; 
		cliUpdatePath = updatesPath + "cli-updates.txt"; 
		guiUpdatesPath = updatesPath + "gui-updates.txt"; 
		activeSeasonPath = dataPath + "active-season.txt";
	}
}
