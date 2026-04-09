import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Add place -> Update Place with new address -> Get Place to validate if new
		// address is present in response

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(PayLoad.addPlaceData()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Access-Control-Allow-Methods", equalTo("POST"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").body("status", equalTo("OK")).extract().response()
				.asString();

		System.out.println("Response: " + response);
		JsonPath jsp = new JsonPath(response);
		String status = jsp.getString("status");
		String place_id = jsp.getString("place_id");
		String scope = jsp.getString("scope");
		String id = jsp.getString("id");

		System.out.println("Status: " + status);
		System.out.println("Place ID: " + place_id);
		System.out.println("Scope: " + scope);
		System.out.println("ID: " + id);

		// Update Place with new address and validate if new address is present in
		// response

		String newAddress = "70 Fiji layout, Spain";
		String res1 = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\n" + "\"place_id\":\"" + place_id + "\",\n" + "\"address\":\"" + newAddress + "\",\n"
						+ "\"key\":\"qaclick123\"\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath jsp3 = ReusableMethods.rawToJson(res1);
		System.out.println("Response after updating place: " + res1);
		String msg = jsp3.getString("msg");
		System.out.println("Message: " + msg);

		// Retrieve Place to validate if new address is present in response

		String res2 = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).when()
				.get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();
		JsonPath jsp2 = ReusableMethods.rawToJson(res2);
		System.out.println("Response after retrieving place: " + res2);
		String address = jsp2.getString("address");
		System.out.println("Address: " + address);
		Assert.assertEquals(address, newAddress);
	}

}
