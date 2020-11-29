package utils;
 
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
 
public class HttpJsonResponse {
 
    public static JsonNode createErrorResponse(String errorMessage) {
        ObjectNode result = Json.newObject();
        result.put("status", 0);
		result.put("errorMessage", Json.toJson(errorMessage));
        return Json.toJson(result);
    }
    public static JsonNode createSuccessResponse(Object data) {
        ObjectNode result = Json.newObject();
        result.put("status", 1);
		result.put("data", Json.toJson(data));
 
        return Json.toJson(result);
    }
}
