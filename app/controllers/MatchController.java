package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import utils.HttpJsonResponse;
import dao.PremierLeagueManagerDAO;
import domain.PremierLeagueManager;
import domain.model.Season;
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
		MatchDTO matchDTO = Json.fromJson(req.body().asJson(), MatchDTO.class);
		boolean isMatchAdded = matchService.addMatch(matchDTO);
		if(isMatchAdded) {
			JsonNode response = HttpJsonResponse.createSuccessResponse(matchDTO);
			return ok(response);
		} else {
			data.put("errorMessage", "Given club(s) has been removed or doesn't exist in the league");
			JsonNode response = HttpJsonResponse.createErrorResponse(data);
			return ok(response);
		}
	}

}
