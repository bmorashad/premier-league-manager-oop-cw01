package domain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import domain.entity.Match;
import domain.entity.FootballClub;
import domain.custom.exception.NoMoreClubsAllowed;

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
		if (isClubExist(footballClub)) {
			return false;
		}
		footballClubs.add(footballClub);
		return true;
	}

	// helper
	public boolean isClubExist(FootballClub footballClub) {
		boolean clubNotExist = footballClubs.stream().
			filter(club -> club.equals(footballClub)).
			findFirst().isEmpty();
		return !clubNotExist;
	}
	
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
	public void addMatch(Match match) {
		FootballClub teamA = match.getTeamA();
		FootballClub teamB  = match.getTeamB();
		int teamAGoals = match.getTeamAGoals();
		int teamBGoals = match.getTeamBGoals();
		teamA.addGoals(teamAGoals, teamBGoals);
		teamB.addGoals(teamBGoals, teamBGoals);
		if(teamAGoals != teamBGoals) {
			match.getWinningTeam().addWinningMatch();
			match.getDefeatedTeam().addDefeatedMatch();
		} else {
			teamA.addDrawMatch();
			teamB.addDrawMatch();
		}
		matches.add(match);
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
