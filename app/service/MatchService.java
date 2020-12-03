package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
// import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import dto.MatchDTO;
import utils.UpdateLogger;
import dao.PremierLeagueManagerDAO;
// import domain.PremierLeagueManager;
import domain.model.Match;
import domain.model.FootballClub;

public class MatchService {
	private static final int MAX_GOALS = 20;
	private static Match lastGeneratedMatch;
	private PremierLeagueManagerDAO plmDAO;
	private UpdateLogger ul;
	private FootballClubService footballClubService;
	private PremierLeagueService premierLeagueService;

	public MatchService() {
		plmDAO = PremierLeagueManagerDAO.getInstance();
		plmDAO.getPremierLeagueManagerByActiveSeason();
		footballClubService = new FootballClubService();
		premierLeagueService = new PremierLeagueService();
		ul = new UpdateLogger("gui");
	}

	public List<Match> getAllMatches() {
		plmDAO.syncUpdates("cli");
		return plmDAO.getPremierLeagueManager().getMatches();
	}
	public List<Match> getMatchesByDate(LocalDate date) {
		plmDAO.syncUpdates("cli");
		return plmDAO.getPremierLeagueManager().getMatches()
			.stream().filter(match -> match.getDate().equals(date))
			.collect(Collectors.toList());
	}
	public Match createRandomMatch() {
		Match generatedMatch = generateMatch();
		if(generatedMatch != null) {
			addMatch(generatedMatch);
			lastGeneratedMatch = generatedMatch;
			return lastGeneratedMatch;
		}
		return null;
	}
	private void addMatch(Match match) {
		System.out.println(match);
		plmDAO.syncUpdates("cli");
		plmDAO.getPremierLeagueManager().addMatch(match);
		ul.logMatchUpdate(match, "CREATE");
	}

	// helper
	public Match generateMatch() {
		List<FootballClub> footballClubs = footballClubService.getAllFootballClubs();
			if(footballClubs.size() < 2) {
					return null;
			}
			List<FootballClub> randomTwoTeams = generateTwoTeams(footballClubs);
			FootballClub  teamA = randomTwoTeams.get(0);
			FootballClub  teamB = randomTwoTeams.get(1);
			while(!isTeamDeferFromLastGeneratedMatch(teamA, teamB) && footballClubs.size() > 2) {
				randomTwoTeams = generateTwoTeams(footballClubs);
				teamA  = randomTwoTeams.get(0);
				teamB  = randomTwoTeams.get(1);
			}
			int teamAGoals = generateGoal(MAX_GOALS);
			int teamBGoals = generateGoal(MAX_GOALS);
			while(!isGoalDeferFromLastGeneratedMatch(teamAGoals, teamBGoals)) {
				teamAGoals = generateGoal(MAX_GOALS);
				teamBGoals = generateGoal(MAX_GOALS);
			}
			LocalDate date = generateDateBySeason();
			Match match = new Match(teamA, teamB, teamAGoals, teamBGoals, date);
			return match;
	}
	// both min and max inclusive
	private static int generateRandomInt(int min, int max) {
		Random rd = new Random();
		return rd.nextInt((max-min)+1) + min;
	}
	private static boolean isTeamDeferFromLastGeneratedMatch(FootballClub teamA, FootballClub teamB) {
		if(lastGeneratedMatch == null) {
			return true;
		}
		boolean isTeamAInLastMatch = teamA.getClubName() == lastGeneratedMatch.getTeamA().getClubName() || 
			teamA.getClubName() == lastGeneratedMatch.getTeamB().getClubName();
		boolean isTeamBInLastMatch = teamB.getClubName() == lastGeneratedMatch.getTeamA().getClubName() || 
			teamB.getClubName() == lastGeneratedMatch.getTeamB().getClubName();
		return !(isTeamAInLastMatch && isTeamBInLastMatch);
	}
	private static boolean isGoalDeferFromLastGeneratedMatch(int teamAGoals, int teamBGoals) {
		if(lastGeneratedMatch == null) {
			return true;
		}
		return !(teamAGoals == lastGeneratedMatch.getTeamAGoals() && 
				teamBGoals == lastGeneratedMatch.getTeamBGoals());
	}
	private static int generateGoal(int max) {
		int probability = generateRandomInt(0, 160);
		int fiveGoalsProbability = 140; 
		int tenGoalsProbability = 150; 
		int fifteenGoalsProbability = 155; 
		if(probability <= fiveGoalsProbability) {
			return generateRandomInt(0, 5);
		} else if(probability <=tenGoalsProbability) {
			return generateRandomInt(6, 10);
		} else if(probability <= fifteenGoalsProbability)  {
			return generateRandomInt(11, 15);
		} else {
			return generateRandomInt(15, max);
		}
	}

	private LocalDate generateDateBySeason() {
		int firstYear = premierLeagueService.getPremierLeagueSeason().getFirstYear();
		int secondYear = premierLeagueService.getPremierLeagueSeason().getSecondYear();
		int matchYear = generateRandomInt(firstYear, secondYear);
		int matchMonth = generateRandomInt(1, 12);
		int matchDay = generateDay(matchYear, matchMonth);
		LocalDate matchDate = LocalDate.of(matchYear, matchMonth, matchDay);
		if(matchDate.compareTo(LocalDate.now()) == -1) {
			return generateDateBySeason();
		}
		return matchDate;
	}
	private static List<FootballClub> generateTwoTeams(List<FootballClub> footballClubs) {
		if(footballClubs.size() > 1) {
			FootballClub teamA = generateTeam(footballClubs);
			FootballClub teamB = generateTeam(footballClubs);
			while(teamA.getClubName().equals(teamB.getClubName())) {
				teamB = generateTeam(footballClubs);
			}
			return Arrays.asList(teamA, teamB);
		}
		return Arrays.asList(); 
	}
	private static FootballClub generateTeam(List<FootballClub> footballClubs) {
		int numOfTeams = footballClubs.size();
		FootballClub teamA = footballClubs.get(generateRandomInt(0, numOfTeams-1));
		return teamA;
	}
	private static int generateDay(int year, int month){
		int maxDaysForMonth = getMaxDaysForMonth(year, month);
		return generateRandomInt(1, maxDaysForMonth);
	}
	private static boolean isLeapYear(int year) {
		boolean isLeapYear = false;
		if(year % 2 == 0) {
			if(year % 100 == 0) {
				if(year % 400 == 0) {
					isLeapYear = true;
				} else {
					isLeapYear = false;
				}
			} else {
				isLeapYear = true;
			}
		}	
		return isLeapYear;
	}
	private static int getMaxDaysForMonth(int year, int month) {
		List<Integer> monthWith31Days = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
		List<Integer> monthWith30Days = Arrays.asList(4, 6, 9, 11);
		if(month == 2) {
			if(isLeapYear(year)) {
				return 28;
			} else {
				return 29;
			}
		} else if(monthWith31Days.contains(month)) {
			return 31;
		} else if(monthWith30Days.contains(month)) {
			return 30;
		} 	
		return 0;
	}

}
