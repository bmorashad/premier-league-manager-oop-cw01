package dao;
import utils.FileOperation;
import utils.ObjectOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import domain.PremierLeagueManager;
import domain.model.Season;
import domain.model.FootballClub;
import domain.model.Match;
import conf.PathConfiguration;

public class PremierLeagueManagerDAO {
	private static volatile PremierLeagueManagerDAO plmDAO;
	private static PremierLeagueManager plm;
	private static final String databaseDir = PathConfiguration.dataPath;
	private static final String cliUpdatesPath = PathConfiguration.cliUpdatePath;
	private static final String guiUpdatesPath = PathConfiguration.guiUpdatesPath;
	private static final String activeSeasonPath = PathConfiguration.activeSeasonPath;

	private PremierLeagueManagerDAO() { }

	public void initPremierLeagueManager(Season season) {
		String filePath = databaseDir + season.toString();
		ObjectOperation oo = new ObjectOperation();
		plm = (PremierLeagueManager) oo.deserialize(filePath);
		if(plm == null) {
			plm = new PremierLeagueManager(season);
		}
	}
	public PremierLeagueManager getPremierLeagueManagerByActiveSeason(){
		String activeSeason = getActiveSeason();
		initPremierLeagueManager(Season.parse(activeSeason));
		return plm;
	}
	public PremierLeagueManager getPremierLeagueManager(){
		return plm;
	}
	public static PremierLeagueManagerDAO getInstance() {
		PremierLeagueManagerDAO _plmDAO = plmDAO;
		if(_plmDAO != null) {
			return plmDAO;
		}
		synchronized(PremierLeagueManagerDAO.class) {
			if(plmDAO == null) {
				plmDAO = new PremierLeagueManagerDAO();
			}
			return plmDAO;
		}
	}
	public void save(PremierLeagueManager plm) {
		String filePath =  databaseDir + plm.SEASON.toString();
		ObjectOperation oo = new ObjectOperation();
		oo.serialize(filePath, plm);
	}
	public void syncUpdates(String type) {
		String updateFile = type.toLowerCase() == "cli" ? cliUpdatesPath : guiUpdatesPath;
		String[] models = {Match.class.getSimpleName().toUpperCase(), 
			FootballClub.class.getSimpleName().toUpperCase()};
		File file = new File(updateFile);
		Scanner sc;
		if(file.exists()) {
			try {
				sc = new Scanner(file);
				while (sc.hasNext()) {
					String[] update = sc.nextLine().split(":");
					String updateType = update[1];
					if(update[0].equals(models[0])){
						Match match = readMatchUpdate(update);
						syncMatchUpdates(match, updateType);
					} else if (update[0].equals(models[1])) {
						FootballClub footballClub = readFootballClub(update);
						syncFootballClubUpdates(footballClub, updateType);
					}
				}
				FileOperation fo = new FileOperation(updateFile);
				fo.write("");
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private Match readMatchUpdate(String[] update) {
		String teamAName = update[2].split("=")[1];
		FootballClub teamA = plm.getClubByName(teamAName);
		String teamBName = update[3].split("=")[1];
		FootballClub teamB = plm.getClubByName(teamBName);
		int teamAGoals = Integer.parseInt(update[4].split("=")[1]);
		int teamBGoals = Integer.parseInt(update[5].split("=")[1]);
		LocalDate date = LocalDate.parse(update[6].split("=")[1]);
		Match match = new Match(teamA, teamB, teamAGoals, teamBGoals, date);
		return match;

	}
	private FootballClub readFootballClub(String[] update) {
		String clubName = update[2].split("=")[1];
		String location = update[3].split("=")[1];
		String country = update[4].split("=")[1];
		FootballClub footballClub = new FootballClub(clubName, country, location);
		return footballClub;

	}
	private void syncFootballClubUpdates(FootballClub footballClub, String updateType) {
		if(updateType.equals("CREATE")) {
			plm.addFootballClub(footballClub);
		} else if(updateType.equals("DELETE")) {
			plm.removeFootballClub(footballClub.getClubName());
		}
	}

	private void syncMatchUpdates(Match match, String updateType) {
		if(updateType.equals("CREATE")) {
			plm.addMatch(match);
		}
	}

	private String getActiveSeason() {
		File file = new File(activeSeasonPath);
		Scanner sc;
		String activeSeason = "";
		if(file.exists()) {
			try {
				sc = new Scanner(file);
				activeSeason= sc.nextLine();
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return activeSeason;
	}
}
