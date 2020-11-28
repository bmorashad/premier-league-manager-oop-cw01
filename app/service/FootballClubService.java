package service;

import java.util.ArrayList;
import java.util.List;


import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.model.FootballClub;

public class FootballClubService {
	private PremierLeagueManagerDAO plmDAO;
	public FootballClubService() {
		plmDAO = PremierLeagueManagerDAO.getInstance();
	}
	public List<FootballClub> getAllFootballClubs() {
		List <FootballClub> footballClubs = new ArrayList<>();
		footballClubs = plmDAO.getPremierLeagueManagerByActiveSeason()
			.getAllClubs();
		return footballClubs;
	}

}
