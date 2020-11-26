import java.util.List;

public interface LeagueManager {
	boolean addFootballClub(FootballClub club) throws NoMoreClubsAllowed;
	FootballClub removeFootballClub(String clubName);
	void addMatch(Match match);
	List<FootballClub> getAllClubs();
	FootballClub getClubByName(String clubName);
	// void displayMatches();
}
