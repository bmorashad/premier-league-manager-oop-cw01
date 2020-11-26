package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.Season;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import play.libs.Json;

import service.PremierLeagueService;

public class PremierLeagueController extends Controller {
	private PremierLeagueService premierLeagueService;

	@Inject
	public PremierLeagueController(PremierLeagueService premierLeagueService) {
		this.premierLeagueService = premierLeagueService;
	}

    public Result getSeason() {
		return ok(Json.toJson(premierLeagueService.getPremierLeagueSeason()));

    }

}
