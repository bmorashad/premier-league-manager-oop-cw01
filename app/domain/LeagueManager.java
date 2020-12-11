package domain;
import java.time.LocalDate;
import java.util.List;
import domain.model.Match;
import domain.model.FootballClub;
import domain.custom.exception.NoMoreClubsAllowed;
import domain.custom.exception.NoOpponentFoundException;
import domain.custom.exception.NoSuchClubException;

public interface LeagueManager {
	boolean addFootballClub(FootballClub club) throws NoMoreClubsAllowed;
	FootballClub removeFootballClub(String clubName);
	void addMatch(Match match) throws NoSuchClubException;
	Match addMatch(String teamA, String teamB, int teamAGoals, int teamBGoals, LocalDate date) throws 
		NoSuchClubException, NoOpponentFoundException;
	List<FootballClub> getAllClubs();
	FootballClub getClubByName(String clubName);
	// void displayMatches();
}
