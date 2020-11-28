package utils;

import java.io.File;
import domain.LeagueType;
import domain.model.Match;
import domain.model.FootballClub;
import conf.Configuration;

public class UpdateLogger {
	private String updatesPath; 
	private static final String activeSeason = Configuration.dataPath + "active-season.txt";

	public UpdateLogger(String type) {
		setUpdatesPath(type);
	}
	private void setUpdatesPath(String type){
		switch(type.toLowerCase()) {
			case "cli":
				updatesPath = Configuration.cliUpdatePath;
				break;
			case "gui":
				updatesPath = Configuration.guiUpdatesPath;
				break;
			default:
				updatesPath = Configuration.cliUpdatePath;
		}
	}
	public void clearLogs() {
		FileOperation fo = new FileOperation(updatesPath);
		fo.write("");
	}
	public void clearAllLogs() {
		FileOperation fo = new FileOperation(Configuration.cliUpdatePath);
		fo.write("");
		fo.setPath(Configuration.guiUpdatesPath);
		fo.write("");
	}
	public static void logActiveSeason(String season) {
		String path =  activeSeason;
		FileOperation fo = new FileOperation(path);
		fo.write(season);
	}

	public void logMatchUpdate(Match match, String updateType) {
		String model = Match.class.getName().toUpperCase();
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
			.append(teamBGoals).append(":").append("date")
			.append(date).append(":");
		sb.append("\n");
		fo.append(sb.toString());
	} 
	public void logFootballClubUpdate(FootballClub footballClub, String updateType) {
		String model = FootballClub.class.getName().toUpperCase();
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