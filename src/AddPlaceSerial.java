//Initial Commit
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import POJO.Location;
import POJO.Places;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class AddPlaceSerial {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		Places plc = new Places();
		plc.setAccuracy(50);
		plc.setAddress("30, Portugal layout, New Delhi 6");
		plc.setLanguage("Spanish");
		plc.setPhone_number("9999999999");
		plc.setWebsite("rahulshettyacademy.com");
		plc.setName("Hombre Phoenix");
		ArrayList<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		plc.setTypes(myList);
		Location loc = new Location();
		loc.setLat(-79.383494);
		loc.setLng(-9.383494);
		plc.setLocation(loc);

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType("application/json").build();

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).build();

		RequestSpecification reqSpec = given().spec(req).body(plc);

		String response = reqSpec.when().post("maps/api/place/add/json").then().spec(res).body("scope", equalTo("APP"))
				.header("Access-Control-Allow-Methods", equalTo("POST")).header("Server", "Apache/2.4.52 (Ubuntu)")
				.body("status", equalTo("OK")).extract().response().asString();

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

	}

}
