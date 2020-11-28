package service;

import java.util.ArrayList;
import java.util.List;


import dao.PremierLeagueManagerDAO;
import domain.model.FootballClub;
import domain.PremierLeagueManager;

public class FootballClubService {
	private PremierLeagueManagerDAO plmDAO;
	private PremierLeagueManager plm;
	public FootballClubService() {
		plmDAO = PremierLeagueManagerDAO.getInstance();
		plm = plmDAO.getPremierLeagueManagerByActiveSeason();
	}
	public List<FootballClub> getAllFootballClubs() {
		plmDAO.syncUpdates("cli");
		List <FootballClub> footballClubs = new ArrayList<>();
		footballClubs = plm.getAllClubs();
		return footballClubs;
	}

}
