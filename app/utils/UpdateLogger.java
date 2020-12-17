package utils;

import java.io.File;
import domain.model.Match;
import domain.model.FootballClub;
import conf.PathConfiguration;

public class UpdateLogger {
	private String updatesPath; 
	private static final String ACTIVE_SEASON_PATH = PathConfiguration.ACTIVE_SEASON_PATH;

	public UpdateLogger(String type) {
		setUpdatesPath(type);
	}
	private void setUpdatesPath(String type){
		switch(type.toLowerCase()) {
			case "cli":
				updatesPath = PathConfiguration.CLI_UPDATES_PATH;
				break;
			case "gui":
				updatesPath = PathConfiguration.GUI_UPDATES_PATH;
				break;
			default:
				updatesPath = PathConfiguration.CLI_UPDATES_PATH;
		}
	}
	public void clearLogs() {
		FileOperation fo = new FileOperation(updatesPath);
		fo.write("");
	}
	public static void clearAllLogs() {
		FileOperation fo = new FileOperation(PathConfiguration.CLI_UPDATES_PATH);
		fo.write("");
		fo.setPath(PathConfiguration.GUI_UPDATES_PATH);
		fo.write("");
	}
	public static void logActiveSeason(String season) {
		String path =  ACTIVE_SEASON_PATH;
		FileOperation fo = new FileOperation(path);
		fo.write(season);
	}

	public void logMatchUpdate(Match match, String updateType) {
		String model = Match.class.getSimpleName().toUpperCase();
		String pathToLog = updatesPath;
		StringBuilder sb = new StringBuilder();
		createFile(pathToLog);
		FileOperation fo = new FileOperation(pathToLog);
		String teamA = match.getTeamA().getClubName();
		String teamB = match.getTeamB().getClubName();
		String teamAGoals = match.getTeamAGoals() + "";
		String teamBGoals = match.getTeamBGoals() + "";
		String date = match.getDate().toString();

		sb.append(model + ":" + updateType + ":").append("teamA=")
			.append(teamA).append(":").append("teamB=")
			.append(teamB).append(":").append("teamAGoals=")
			.append(teamAGoals).append(":").append("teamBGoals=")
			.append(teamBGoals).append(":").append("date=")
			.append(date).append(":");
		sb.append("\n");
		fo.append(sb.toString());
	} 
	public void logFootballClubUpdate(FootballClub footballClub, String updateType) {
		String model = FootballClub.class.getSimpleName().toUpperCase();
		createFile(updatesPath);
		StringBuilder sb = new StringBuilder();
		FileOperation fo = new FileOperation(updatesPath);

		sb.append(model + ":" + updateType + ":");
		sb.append("clubName=").append(footballClub.getClubName()).append(":")
			.append("country=").append(footballClub.getCountry()).append(":")
			.append("location=").append(footballClub.getLocation()).append(":")
			.append("\n");
		fo.append(sb.toString());
	} 
	private boolean createFile(String path) {
		boolean isFileCreated = false;
		File file = new File(path);
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			return isFileCreated = true; 
		}
		return isFileCreated;
	}

}
