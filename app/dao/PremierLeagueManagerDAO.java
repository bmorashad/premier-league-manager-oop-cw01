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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import domain.PremierLeagueManager;
import domain.Season;
import domain.entity.FootballClub;
import domain.entity.Match;
import dto.MatchDTO;
import javax.inject.Singleton;

@Singleton
public class PremierLeagueManagerDAO {
	private PremierLeagueManager plm;
	private final String databaseDir = "/home/bmora/Documents/IIT_L5/OOP/CW01/PremierLeague Manager CLI/Database/";
	private final String activeSeasonFilePath = databaseDir + "active-season";
	private final String cliUpdatesPath = databaseDir + ".updates/cli/";
	private final String guiUpdatesPath = databaseDir + ".updates/gui/";
	private final String professionalLeaguePath = "professional-league/";
	private final String guiAddedMatches = "gui-added-matches.txt";
	private final String cliRemovedClubs =  "cli-removed-clubs.txt";

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
	private boolean isClubDeleted(String teamName) {
		String cliClubUpdatesPath = cliUpdatesPath + professionalLeaguePath + cliRemovedClubs;
		File file = new File(cliClubUpdatesPath);
		List<String> removedClubs = new ArrayList<>();
		Scanner sc;
		if(file.exists()) {
			try {
				sc = new Scanner(file);
				while (sc.hasNext()) {
					String[] update = sc.nextLine().split(":");
					String clubName = update[1].split("=")[1];
					removedClubs.add(clubName);
				}
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return removedClubs.contains(teamName);
	}
	public boolean addMatch(MatchDTO matchDTO) {
		String teamAName= matchDTO.getTeamA();
		String teamBName= matchDTO.getTeamB();
		boolean isTeamADeleted = isClubDeleted(teamAName);
		boolean isTeamBDeleted = isClubDeleted(teamBName);
		boolean isMatchValid = !isTeamADeleted && !isTeamBDeleted;
		FootballClub teamA = null;
		FootballClub teamB = null;
		if(isMatchValid) {
			List<FootballClub> clubsInMatch = plm.getAllClubs().stream()
				.filter(club -> club.getClubName().equals(teamAName) || club.getClubName().equals(teamBName))
				.collect(Collectors.toList());
			for (FootballClub footballClub : clubsInMatch) {
				if(footballClub.getClubName().equals(teamAName)) {
					teamA = footballClub;
				} else {
					teamB = footballClub;
				} 
			}
			if(teamA != null && teamB != null) {
				Match match = new Match(teamA, teamB, matchDTO.getTeamAGoals(), 
					matchDTO.getTeamBGoals(), matchDTO.getDate());
				plm.addMatch(match);
				save(plm);
				return true;
			}
		}
		return false;
	}
	public void logCreatedMatch(Match match) {
		StringBuilder sb = new StringBuilder();
		LocalDateTime time = LocalDateTime.now();
		File file = new File(guiAddedMatches);
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
}
