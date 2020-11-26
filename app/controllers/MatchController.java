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

import service.MatchService;

public class MatchController extends Controller {
	private MatchService matchService;

	@Inject
	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

    public Result getAllMatches() {
		return ok(Json.toJson(matchService.getAllMatches()));

    }

}
