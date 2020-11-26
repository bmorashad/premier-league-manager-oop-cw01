package service;

import javax.inject.Inject;

import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;

public class PremierLeagueService {
	@Inject
	private PremierLeagueManagerDAO plmDAO;

	public String getPremierLeagueSeason() {
		return plmDAO.getPremierLeagueManager().SEASON.toString();
	}
}
