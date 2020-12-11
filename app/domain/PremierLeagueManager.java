package domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import domain.model.Match;
import domain.model.Season;
import domain.model.FootballClub;
import domain.custom.exception.NoMoreClubsAllowed;
import domain.custom.exception.NoSuchClubException;

public class PremierLeagueManager implements Serializable, LeagueManager{
	public static final long serialVersionUID = 88L;

	public final int MAX_CLUBS = 20;
	public final Season SEASON;

	private List<FootballClub> footballClubs;
	private List<Match> matches;

	public PremierLeagueManager(Season SEASON) {
		this.SEASON = SEASON;
		footballClubs = new ArrayList<>();
		matches = new ArrayList<>();
	}

	public List<Match> getMatches() {
		return matches;
	}

	@Override
	public boolean addFootballClub(FootballClub footballClub) {
		int numOfClubs = footballClubs.size();
		if(numOfClubs == MAX_CLUBS) {
			throw new NoMoreClubsAllowed("No more clubs can be added :/");
		}
		if (getClubByName(footballClub.getClubName()) != null) {
			return false;
		}
		footballClubs.add(footballClub);
		return true;
	}

	// helper
	// public boolean isClubExist(FootballClub footballClub) {
		// boolean clubNotExist = footballClubs.stream().
			// filter(club -> club.equals(footballClub)).
			// findFirst().isEmpty();
		// return !clubNotExist;
	// }
	
	@Override
	public FootballClub removeFootballClub(String clubName) {
		FootballClub removedClub = null;
		for (int i = 0; i < footballClubs.size(); i++) {
			if (footballClubs.get(i).getClubName().equals(clubName)) {
				removedClub = footballClubs.remove(i);
				break;
			}
		}
		return removedClub;
	}

	@Override
	@Deprecated
	public void addMatch(Match match) {
		FootballClub teamA = getClubByName(match.getTeamA().getClubName());
		FootballClub teamB  = getClubByName(match.getTeamB().getClubName());
		if(teamA == null || teamB == null) {
			throw new NoSuchClubException("No such club in the premier league!");
		}
		int teamAGoals = match.getTeamAGoals();
		int teamBGoals = match.getTeamBGoals();
		LocalDate date = match.getDate();
		teamA.addGoals(teamAGoals, teamBGoals);
		teamB.addGoals(teamBGoals, teamAGoals);
		if(teamAGoals != teamBGoals) {
			match.getWinningTeam().addWinningMatch();
			match.getDefeatedTeam().addDefeatedMatch();
		} else {
			teamA.addDrawMatch();
			teamB.addDrawMatch();
		}
		matches.add(new Match(teamA, teamB, teamAGoals, teamBGoals, date));
	}
	@Override
	public Match addMatch(String teamA, String teamB, int teamAGoals, int teamBGoals, LocalDate date) {
		FootballClub clubA = getClubByName(teamA);
		FootballClub clubB  = getClubByName(teamB);
		if(clubA == null || clubB == null) {
			throw new NoSuchClubException("No such club in the premier league!");
		}
		clubA.addGoals(teamAGoals, teamBGoals);
		clubB.addGoals(teamBGoals, teamAGoals);
		Match match = new Match(clubA, clubB, teamAGoals, teamBGoals, date);
		if(teamAGoals != teamBGoals) {
			match.getWinningTeam().addWinningMatch();
			match.getDefeatedTeam().addDefeatedMatch();
		} else {
			clubA.addDrawMatch();
			clubB.addDrawMatch();
		}
		matches.add(match);
		return match;
	}
	@Override
	public List<FootballClub> getAllClubs() {
		return footballClubs;
	}

	@Override
	public FootballClub getClubByName(String clubName) {
		FootballClub footballClub = footballClubs.stream().
			filter(club -> club.getClubName().equalsIgnoreCase(clubName)).
			findFirst().orElse(null);
		return footballClub;
	}

	public int getNumOfClubs() {
		return footballClubs.size();
	}
	@Override
	public String toString() {
		return footballClubs.get(1).toString();
	}
}
