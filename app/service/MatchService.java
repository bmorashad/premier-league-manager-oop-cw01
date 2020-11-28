package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dto.MatchDTO;
import utils.UpdateLogger;
import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.model.Match;
import domain.model.FootballClub;

public class MatchService {
	private PremierLeagueManagerDAO plmDAO;
	private PremierLeagueManager plm;
	private UpdateLogger ul;

	public MatchService() {
		plmDAO = PremierLeagueManagerDAO.getInstance();
		plm = plmDAO.getPremierLeagueManagerByActiveSeason();
		ul = new UpdateLogger("gui");
	}

	public List<MatchDTO> getAllMatches() {
		plmDAO.syncUpdates("cli");
		List<MatchDTO> matches = new ArrayList<>();
		matches = plm.getMatches().stream()
			.map(match -> matchToMatchDTO(match)).collect(Collectors.toList());
		return matches;
	}
	public boolean addMatch(MatchDTO matchDTO) {
		plmDAO.syncUpdates("cli");
		Match match = matchFromMatchDTO(matchDTO).orElse(null);
		if(match == null) {
			return false;
		}
		System.out.println(match.getTeamAGoals());
		ul.logMatchUpdate(match, "CREATE");
		return true;
	}
	private MatchDTO matchToMatchDTO(Match match) {
		String teamA = match.getTeamA().getClubName();
		String teamB = match.getTeamB().getClubName();
		int teamAGoals = match.getTeamAGoals();
		int teamBGoals = match.getTeamBGoals();
		LocalDate date = match.getDate();
		return new MatchDTO(teamA, teamB, teamAGoals, teamBGoals, date);
	} 
	private Optional<Match> matchFromMatchDTO(MatchDTO matchDTO) {
		String teamAName= matchDTO.getTeamA();
		String teamBName= matchDTO.getTeamB();
		Match match = null;
		FootballClub teamA = null;
		FootballClub teamB = null;
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
			match = new Match(teamA, teamB, matchDTO.getTeamAGoals(), 
					matchDTO.getTeamBGoals(), matchDTO.getDate());
			plm.addMatch(match);
		}
		return Optional.ofNullable(match);
	} 
}
