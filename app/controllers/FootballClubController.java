package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;


import service.FootballClubService;
import utils.HttpJsonResponse;
import domain.model.FootballClub;
import domain.model.Match;
import domain.model.Season;

public class FootballClubController extends Controller {
	private FootballClubService footballService = new FootballClubService();

   public Result getAllFootballClubs() {
        ObjectNode data = Json.newObject();
		data.put("footballClubs",  Json.toJson(footballService.getAllFootballClubs()));
		JsonNode response = HttpJsonResponse.createSuccessResponse(data);
		return ok(response);

    }

}
