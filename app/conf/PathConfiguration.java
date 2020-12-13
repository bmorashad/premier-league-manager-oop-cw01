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
		String root = System.getProperty("user.dir");
		String[] folders = root.split("/");
		int indexOfProjectRootFolder = Arrays.asList(folders).lastIndexOf(projectName);
		projectRoot = String.join("/", Arrays.copyOfRange(folders, 0, indexOfProjectRootFolder + 1)) + "/";
		dataPath = projectRoot + "app/.data/";
		updatesPath = dataPath + ".updates/"; 
		cliUpdatePath = updatesPath + "cli-updates.txt"; 
		guiUpdatesPath = updatesPath + "gui-updates.txt"; 
		activeSeasonPath = dataPath + "active-season.txt";
	}
}
