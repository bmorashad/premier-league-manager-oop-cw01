package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.MatchDTO;
import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.model.Match;

public class MatchService {
	private PremierLeagueManagerDAO plmDAO = new PremierLeagueManagerDAO();

	public List<MatchDTO> getAllMatches() {
		PremierLeagueManager plm = plmDAO.getPremierLeagueManager();
		List<MatchDTO> matches = new ArrayList<>();
		matches = plm.getMatches().stream()
			.map(match -> matchToMatchDTO(match)).collect(Collectors.toList());
		return matches;
	}
	public boolean addMatch(MatchDTO matchDTO) {
		boolean isMatchAdded = plmDAO.addMatch(matchDTO);
		return isMatchAdded;
	}
	private MatchDTO matchToMatchDTO(Match match) {
		String teamA = match.getTeamA().getClubName();
		String teamB = match.getTeamB().getClubName();
		int teamAGoals = match.getTeamAGoals();
		int teamBGoals = match.getTeamBGoals();
		LocalDate date = match.getDate();
		return new MatchDTO(teamA, teamB, teamAGoals, teamBGoals, date);
	} 
}
