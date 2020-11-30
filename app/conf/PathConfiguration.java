package conf;
public class PathConfiguration {
	public static final String projectRoot = "/home/bmora/Documents/IIT_L5/OOP/Premier League Manager/"; 
	public static final String projectFinalBuildExecutable = projectRoot + "premier-league-manager/premier-league/target/universal/stage/bin/premier-league"; 
	public static final String dataPath = projectRoot + "premier-league-manager/premier-league/.data/";
	public static final String updatesPath = dataPath + ".updates/"; 
	public static final String cliUpdatePath = updatesPath + "cli-updates.txt"; 
	public static final String guiUpdatesPath = updatesPath + "gui-updates.txt"; 
	public static final String activeSeasonPath = dataPath + "active-season.txt";
}
