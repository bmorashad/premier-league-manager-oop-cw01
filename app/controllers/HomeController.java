package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.PremierLeagueManagerDAO;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

import javax.inject.Inject;

public class HomeController extends Controller {
	@Inject
	private PremierLeagueManagerDAO plmDAO;
	class Person {
		String name;
		public Person(String name) {
			this.name = name;
		}
	}

    public Result index(Http.Request req, String id) {

		ObjectNode result = Json.newObject();
		JsonNode reqBody = req.body().asJson();

		Person p = new Person(reqBody.get("name").asText());
		JsonNode personJson = Json.toJson(p);

		result.put("status", 1);
		result.put("name", reqBody.get("name").asText());
		result.put("json", personJson);


        return ok(result);
    }

}
