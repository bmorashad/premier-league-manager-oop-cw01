package service;

import dao.PremierLeagueManagerDAO;
import domain.model.Season;

public class PremierLeagueService {
	private PremierLeagueManagerDAO plmDAO;

	public PremierLeagueService() {
		plmDAO = PremierLeagueManagerDAO.getInstance();
		plmDAO.getPremierLeagueManagerByActiveSeason();
	}
	public Season getPremierLeagueSeason() {
		return plmDAO.getPremierLeagueManager().SEASON;
	}
}
