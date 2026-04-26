import static io.restassured.RestAssured.given;

import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.Matchers.*;

public class SchemaValidation {

	public static void main(String[] args) {

		String place_id = "15c484fbe943318c790a8ac5dd0be86d";
		String response = given().baseUri("https://rahulshettyacademy.com").queryParam("key", "qaclick123")
				.queryParam("place_id", place_id).when().get("maps/api/place/get/json").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("blood-unit-schema.json")).statusCode(200)

				.extract().response().asString();

		;

		System.out.println(response);

	}

}
