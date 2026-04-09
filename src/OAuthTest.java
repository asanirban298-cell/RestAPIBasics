import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.GetCourse;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Get Access token from authorization server
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when().post("oauthapi/oauth2/resourceOwner/token").then().assertThat()
				.statusCode(200).extract().asString();

		JsonPath js = ReusableMethods.rawToJson(response);
		String access_token = js.getString("access_token");
		System.out.println(access_token);

		// Get Course Details from server using the access-token using JsonPath

		/*
		 * response = given().queryParam("access_token",
		 * access_token).when().get("oauthapi/getCourseDetails").then()
		 * .assertThat().statusCode(401).extract().response().asString(); js =
		 * ReusableMethods.rawToJson(response); String instructor =
		 * js.getString("instructor"); String url = js.getString("url"); String
		 * courseTitle = js.getString("courses.webAutomation.courseTitle");
		 * System.out.println(instructor + " : " + url + " : " + courseTitle);
		 */

		// Get Course Details from server using the access-token using POJO
		// DeSerialization

		GetCourse gc = given().queryParam("access_token", access_token).when().get("oauthapi/getCourseDetails").then()
				.assertThat().statusCode(401).extract().response().as(GetCourse.class);
		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getExpertise());
		System.out.println(gc.getServices());
		System.out.println(gc.getUrl());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		for (int i = 0; i < gc.getCourses().getApi().size(); i++) {

			if (gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {

				System.out.println(gc.getCourses().getApi().get(i).getPrice());

			}

		}

		String[] webAutoCourseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		ArrayList<String> courseTitles = new ArrayList<String>();
		for (int i = 0; i < gc.getCourses().getWebAutomation().size(); i++) {

			System.out.println(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
			courseTitles.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());

		}
		List<String> expectedList = Arrays.asList(webAutoCourseTitles);
		Assert.assertTrue(courseTitles.equals(expectedList));

	}

}
