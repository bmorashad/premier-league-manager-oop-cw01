package service;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;


import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.entity.FootballClub;

public class FootballClubService {
	@Inject
	PremierLeagueManagerDAO plmDAO;

	public List<FootballClub> getAllFootballClubs() {
		List <FootballClub> footballClubs = new ArrayList<>();
		footballClubs = plmDAO.getPremierLeagueManager().getAllClubs();
		return footballClubs;
	}

}
