package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import utils.HttpJsonResponse;
import domain.model.FootballClub;
import domain.model.Match;
import domain.model.Season;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import play.libs.Json;

import dto.MatchDTO;
import service.MatchService;
import javax.inject.Inject;

public class MatchController extends Controller {
	MatchService matchService;

	@Inject
	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

    public Result getAllMatches() {
		ObjectNode data = Json.newObject();
		List<MatchDTO> matches = matchService.getAllMatches().stream()
			.map(match -> matchToMatchDTO(match)).collect(Collectors.toList());
		data.put("matches", Json.toJson(matches));
		JsonNode response = HttpJsonResponse.createSuccessResponse(data);
		return ok(response);

    }
	public Result createRandomMatch() {
		ObjectNode data = Json.newObject();
		Match match = matchService.createRandomMatch();
		if(match != null) {
			MatchDTO matchDTO = matchToMatchDTO(match);
			data.put("match", matchDTO);
			JsonNode response = HttpJsonResponse.createSuccessResponse(data);
			return ok(response);
		}
		if(isMatchAdded) {
			JsonNode response = HttpJsonResponse.createErrorResponse("Not enough clubs to generate a match");
			return ok(response);
		} 	
	}
	private MatchDTO matchToMatchDTO(Match match) {
		String teamA = match.getTeamA().getClubName();
		String teamB = match.getTeamB().getClubName();
		int teamAGoals = match.getTeamAGoals();
		int teamBGoals = match.getTeamBGoals();
		LocalDate date = match.getDate();
		return new MatchDTO(teamA, teamB, teamAGoals, teamBGoals, date);

	} 

}
