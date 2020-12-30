package conf;

import java.util.Arrays;

public class PathConfiguration {
	public static final String PROJECT_NAME = "premier-league-manager-oop-cw01"; 
	public static final String PROJECT_ROOT; 
	public static final String DATA_PATH;
	public static final String UPDATES_PATH; 
	public static final String CLI_UPDATES_PATH; 
	public static final String GUI_UPDATES_PATH; 
	public static final String ACTIVE_SEASON_PATH;

	static {
		String pathSeparator = "/";
		String root = System.getProperty("user.dir");
		root = root.replace("\\\\", pathSeparator);
		root = root.replace("\\", pathSeparator);
		String[] folders = root.split(pathSeparator);
		int indexOfProjectRootFolder = Arrays.asList(folders).lastIndexOf(PROJECT_NAME);
		PROJECT_ROOT = String.join(pathSeparator, Arrays.copyOfRange(folders, 0, indexOfProjectRootFolder + 1)) + pathSeparator;
		DATA_PATH = PROJECT_ROOT + ".data/";
		UPDATES_PATH = DATA_PATH + ".updates/"; 
		CLI_UPDATES_PATH = UPDATES_PATH + "cli-updates.txt"; 
		GUI_UPDATES_PATH = UPDATES_PATH + "gui-updates.txt"; 
		ACTIVE_SEASON_PATH = DATA_PATH + "active-season.txt";
	}
}
