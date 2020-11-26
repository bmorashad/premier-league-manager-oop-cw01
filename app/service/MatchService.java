package service;

import javax.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import service.dto.MatchDTO;
import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.Match;

public class MatchService {
	@Inject
	private PremierLeagueManagerDAO plmDAO;

	public List<MatchDTO> getAllMatches() {
		PremierLeagueManager plm = plmDAO.getPremierLeagueManager();
		List<MatchDTO> matches = new ArrayList<>();
		matches = plm.getMatches().stream()
			.map(match -> matchToMatchDTO(match)).collect(Collectors.toList());
		return matches;
	}
	public boolean addMatch() {
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
}
