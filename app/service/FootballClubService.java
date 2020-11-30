package service;

import java.util.ArrayList;
import java.util.List;


import dao.PremierLeagueManagerDAO;
import domain.model.FootballClub;

public class FootballClubService {
	private PremierLeagueManagerDAO plmDAO;
	public FootballClubService() {
		plmDAO = PremierLeagueManagerDAO.getInstance();
		plmDAO.getPremierLeagueManagerByActiveSeason();
	}
	public List<FootballClub> getAllFootballClubs() {
		plmDAO.syncUpdates("cli");
		List <FootballClub> footballClubs = new ArrayList<>();
		footballClubs = plmDAO.getPremierLeagueManager().getAllClubs();
		return footballClubs;
	}

}
