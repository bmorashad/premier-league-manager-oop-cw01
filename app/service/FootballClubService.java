package service;

import java.util.ArrayList;
import java.util.List;


import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.model.FootballClub;

public class FootballClubService {
	private PremierLeagueManagerDAO plmDAO = new PremierLeagueManagerDAO();

	public List<FootballClub> getAllFootballClubs() {
		List <FootballClub> footballClubs = new ArrayList<>();
		footballClubs = plmDAO.getPremierLeagueManager().getAllClubs();
		return footballClubs;
	}

}
