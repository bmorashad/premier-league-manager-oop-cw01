package service;

import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;

public class PremierLeagueService {
	private PremierLeagueManagerDAO plmDAO = new PremierLeagueManagerDAO();

	public String getPremierLeagueSeason() {
		return plmDAO.getPremierLeagueManager().SEASON.toString();
	}
}
