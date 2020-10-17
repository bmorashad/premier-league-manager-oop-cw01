package controllers;

import play.mvc.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


// import jdk.nashorn.internal.ir.ObjectNode;
import play.libs.Json;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	class Person {
		String name;
		public Person(String name) {
			this.name = name;
		}
	}

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
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
