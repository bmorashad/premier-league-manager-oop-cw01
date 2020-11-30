package service;

import dao.PremierLeagueManagerDAO;

public class PremierLeagueService {
	private PremierLeagueManagerDAO plmDAO;

	public PremierLeagueService() {
		plmDAO = PremierLeagueManagerDAO.getInstance();
		plmDAO.getPremierLeagueManagerByActiveSeason();
	}
	public String getPremierLeagueSeason() {
		return plmDAO.getPremierLeagueManager().SEASON.toString();
	}
}
