package dao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import domain.PremierLeagueManager;
import domain.FootballClub;
import domain.Match;
import domain.Season;
import javax.inject.Singleton;

@Singleton
public class PremierLeagueManagerDAO {
	private PremierLeagueManager plm;
	private final String databaseDir = "/home/bmora/Documents/IIT_L5/OOP/CW01/TEST/PremierLeague Manager CLI/Database/";
	private final String activeSeasonFilePath = databaseDir + "active-season";
	private final String guiAddedMatches = databaseDir + "gui-added-matches.txt";
	private final String cliRemoveClubs = databaseDir + "cli-removed-clubs.txt";
	private final String cliAddedMatches = databaseDir + "cli-added-clubs.txt";

	public PremierLeagueManagerDAO() {
		loadActivePremierLeagueManager();
	}

	public void loadActivePremierLeagueManager() {
		File activeSeason = new File(activeSeasonFilePath);
		Scanner sc = null;
			try {
				sc = new Scanner(activeSeason);
				String season = sc.nextLine();
				plm = getPremierLeagueManagerBySeason(season).orElse(null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}
	}
	public PremierLeagueManager getPremierLeagueManager() {
		return plm;
	}
	public Optional<PremierLeagueManager> getPremierLeagueManagerBySeason(String season) {
		String filePath = databaseDir + season;
		File leagueData = new File(filePath);
		System.out.println(leagueData.exists());

		PremierLeagueManager plm = null;
		if (leagueData.exists() && leagueData.isFile()) {
			try {
				FileInputStream fis
					= new FileInputStream(filePath);
				ObjectInputStream ois
					= new ObjectInputStream(fis);
				plm = (PremierLeagueManager) ois.readObject();
				ois.close(); 
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return Optional.ofNullable(plm);
	}

	public void save(PremierLeagueManager plm) {
		String filePath =  databaseDir + plm.SEASON.toString();
		try {
			FileOutputStream fos
				= new FileOutputStream(filePath);
			ObjectOutputStream oos 
				= new ObjectOutputStream(fos);
			oos.writeObject(plm);
			oos.flush();
			oos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public PremierLeagueManager syncAddedMatch(PremierLeagueManager plm) {
		File file = new File(guiAddedMatches);
		Scanner sc;
		if(file.exists()) {
			try {
				sc = new Scanner(file);
				while (sc.hasNext()) {
					// String[] update = sc.nextLine().split(":");
					// String teamAName = update[5].split("=")[1];
					// FootballClub teamA = plm.getClubByName(teamAName);
					// String teamBName = update[7].split("=")[1];
					// FootballClub teamB = plm.getClubByName(teamBName);
					// int teamAGoals = Integer.parseInt(update[9].split("=")[1]);
					// int teamBGoals = Integer.parseInt(update[11].split("=")[1]);
					// LocalDate date = LocalDate.parse(update[13].split("=")[1]);
					// // plm.addMatch(new Match(teamA, teamB, teamAGoals, teamBGoals, date));
				}
				sc.close();
				return plm;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}


		}
		return null;
	}

	public void logCreatedMatch(Match match) {
		StringBuilder sb = new StringBuilder();
		LocalDateTime time = LocalDateTime.now();
		File file = new File(cliAddedMatches);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.append(time.toString() + ":");

			String teamA = match.getTeamA().getClubName();
			String teamB = match.getTeamB().getClubName();
			String teamAGoals = match.getTeamAGoals() + "";
			String teamBGoals = match.getTeamBGoals() + "";
			String date = match.getDate().toString();

			sb.append("teamA=").append(teamA).append(":").append("teamB=").append(teamB).append(":").
				append("teamAGoals=").append(teamAGoals).append(":").
				append("teamBGoals=").append(teamBGoals).append(":").
				append("date").append(date).append(":");

			fw.append(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	public void logRemovedFootballClub(FootballClub footballClub) {
		StringBuilder sb = new StringBuilder();
		LocalDateTime time = LocalDateTime.now();
		File file = new File(cliRemoveClubs);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.append(time.toString() + ":");

			sb.append("clubName=").append(footballClub.getClubName()).append(":");
			fw.append(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
