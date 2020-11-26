package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import play.libs.Json;

import service.FootballClubService;
import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.entity.FootballClub;
import domain.entity.Match;
import domain.Season;

public class FootballClubController extends Controller {
	@Inject
	private FootballClubService footballService ;

   public Result getAllFootballClubs() {
		List<FootballClub> footballClubs = footballService.getAllFootballClubs();
		return ok(Json.toJson(footballClubs));

    }

}
