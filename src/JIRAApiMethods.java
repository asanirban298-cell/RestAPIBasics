import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JIRAApiMethods {

	public static void main(String args[]) {

		RestAssured.baseURI = "https://asanirban298.atlassian.net";

		String response = given().queryParam("updateHistory", "true").header("Content-Type", "application/json").header(
				"Authorization",
				"Basic YXNhbmlyYmFuMjk4QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBhZnZhUGY4bEFUZU1SUzFHNnlZWnlGTGExY3JXVjlvMTgtNGNId1NWZVgxTmhvbHpKSGp3Zkk3QzA1b3FqSGFTa0pab0ZwOWxYVl9yMWh5cmliUGpBeWxRY09XYnZmX2VFR2k3R09VSFlrQXZ5SVlFTXlrMG4zc0Iwa3gzTkpLcUNfVEtVUWY1dTZUM2QxdG53RTB2bVpzU0JwYmN5RWxYQTJIaWxqdW9teVU9MTQ2RjI3RUU=")
				.header("Accept", "application/json")
				.body("{\n" + "    \"fields\": {\n" + "       \"project\":\n" + "       {\n"
						+ "          \"key\": \"SCRUM\"\n" + "       },\n"
						+ "       \"summary\": \"Textboxes are not working\",\n" + "       \"issuetype\": {\n"
						+ "          \"name\": \"Bug\"\n" + "       }\n" + "   }\n" + "}")
				.when().post("rest/api/3/issue").then().assertThat().statusCode(201).extract().response().asString();

		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.getString("id");
		System.out.println(id);

		// Add attachment to the created issue JIRA

		given().pathParam("key", id).header("Authorization",
				"Basic YXNhbmlyYmFuMjk4QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBhZnZhUGY4bEFUZU1SUzFHNnlZWnlGTGExY3JXVjlvMTgtNGNId1NWZVgxTmhvbHpKSGp3Zkk3QzA1b3FqSGFTa0pab0ZwOWxYVl9yMWh5cmliUGpBeWxRY09XYnZmX2VFR2k3R09VSFlrQXZ5SVlFTXlrMG4zc0Iwa3gzTkpLcUNfVEtVUWY1dTZUM2QxdG53RTB2bVpzU0JwYmN5RWxYQTJIaWxqdW9teVU9MTQ2RjI3RUU=")
				.header("X-Atlassian-Token", "no-check")
				.multiPart("file", new File("/Users/anirbansarkar/Pictures/ADI KAILASH TRIP/DSC04360.JPG")).when()
				.post("rest/api/3/issue/{key}/attachments").then().assertThat().statusCode(200);

	}

}
