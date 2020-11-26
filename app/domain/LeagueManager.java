package domain;
import java.util.List;
import domain.entity.Match;
import domain.entity.FootballClub;
import domain.custom.exception.NoMoreClubsAllowed;

public interface LeagueManager {
	boolean addFootballClub(FootballClub club) throws NoMoreClubsAllowed;
	FootballClub removeFootballClub(String clubName);
	void addMatch(Match match);
	List<FootballClub> getAllClubs();
	FootballClub getClubByName(String clubName);
	// void displayMatches();
}
