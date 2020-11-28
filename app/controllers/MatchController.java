package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import utils.HttpJsonResponse;
import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.Season;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import play.libs.Json;

import dto.MatchDTO;
import service.MatchService;

public class MatchController extends Controller {
	private MatchService matchService = new MatchService();

	// @Inject
	// public MatchController(MatchService matchService) {
		// this.matchService = matchService;
	// }

    public Result getAllMatches() {
		ObjectNode data = Json.newObject();
		data.put("matches", Json.toJson(matchService.getAllMatches()));
		JsonNode response = HttpJsonResponse.createSuccessResponse(data);
		return ok(response);

    }
	public Result createMatch(Http.Request req) {
		ObjectNode data = Json.newObject();
		JsonNode reqBody = req.body().asJson();
		String teamA = reqBody.get("teamA").asText();
		String teamB = reqBody.get("teamB").asText();
		int teamAGoals = Integer.parseInt(reqBody.get("teamAGoals").asText());
		int teamBGoals = Integer.parseInt(reqBody.get("teamBGoals").asText());
		LocalDate date = LocalDate.parse(reqBody.get("date").asText());

		MatchDTO matchDTO = new MatchDTO(teamA, teamB, teamAGoals, teamBGoals, date);
		boolean isMatchAdded = matchService.addMatch(matchDTO);
		if(isMatchAdded) {
			JsonNode match = Json.toJson(matchDTO);
			data.put("match", match);
			JsonNode response = HttpJsonResponse.createSuccessResponse(data);
			return ok(response);
		} else {
			data.put("errorMessage", "Give clubs no longer exist in the league");
			JsonNode response = HttpJsonResponse.createErrorResponse(data);
			return ok(response);
		}
	}

}
