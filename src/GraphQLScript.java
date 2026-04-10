import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.sql.Array;

import org.testng.Assert;

public class GraphQLScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		int characId = 17059;
		String response = given().log().all().header("content-type", "application/json").body(
				"{\"query\":\"query ($characterId: Int!, $locationId: Int!, $episodeId: Int!, $name: String!) {\\n  character(characterId: $characterId) {\\n    id\\n    name\\n    type\\n    status\\n    species\\n    gender\\n    image\\n    origin {\\n      id\\n      name\\n      type\\n      dimension\\n      created\\n    }\\n  }\\n  location(locationId: $locationId) {\\n    name\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: $name}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n      location {\\n        id\\n        name\\n        type\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"
						+ characId + ",\"locationId\":17059,\"episodeId\":17059,\"name\":\"ankur\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		System.out.println(js.getString("data.character.name"));

		// Mutation

		int[] locIds = { 30272, 30274 };
		String mutationResponse = given().log().all().header("content-type", "application/json").body(
				"{\"query\":\"mutation {\\n  createLocation(location: {name: \\\"Belfast\\\", type: \\\"Irish\\\", dimension: \\\"City\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: \\\"Tommy Shelby\\\", type: \\\"Gangster\\\", status: \\\"Fugitive\\\", species: \\\"Human\\\", gender: \\\"Male\\\", image: \\\"God\\\", originId: 17061, locationId: 30267}) {\\n    id\\n  }\\n  createEpisode(episode: {name: \\\"Winter is coming!\\\", air_date: \\\"17/10/2009\\\", episode: \\\"1001\\\"}) {\\n    id\\n  }\\n  deleteLocations(locationIds: "
						+ locIds
						+ "){\\n    locationsDeleted\\n  }\\n}\\n\",\"variables\":{\"characterId\":17059,\"episodeId\":17059,\"name\":\"ankur\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();

		System.out.println(mutationResponse);
		js = new JsonPath(mutationResponse);
		System.out.println(js.getInt("data.createLocation.id"));
		System.out.println(js.getInt("data.createCharacter.id"));
		System.out.println(js.getInt("data.createEpisode.id"));
		Assert.assertTrue(js.getInt("data.deleteLocations.locationsDeleted") == locIds.length);
		System.out.println(js.getInt("data.deleteLocations.locationsDeleted"));

	}

}
