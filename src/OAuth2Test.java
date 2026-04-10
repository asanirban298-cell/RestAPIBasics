
/********************UPDATED CODE AS PER GOOGLE UPDATE *****************************/

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import io.restassured.parsing.Parser;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

import io.restassured.response.ResponseBody;

import POJO.Api;

import POJO.GetCourse;

public class OAuth2Test {

	public static void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub

		// Hit this url and login for fresh authorization code and use this url below in url variable

		/*
		 * https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com
		 * /auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&
		 * client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.
		 * googleusercontent.com&response_type=code&redirect_uri=https://
		 * rahulshettyacademy.com/getCourse.php
		 */
		String url = "https://rahulshettyacademy.com/getCourse.php?iss=https%3A%2F%2Faccounts.google.com&code=4%2F0Aci98E8Zv47H9ixlSxAdBTXU3br90CL_UZ1jkIpI71YGSaVvgogIfPF7C_WNKuf4z-MgDw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";

		String partialcode = url.split("code=")[1];

		String code = partialcode.split("&scope")[0];

		System.out.println(code);

		String response =

				given()

						.urlEncodingEnabled(false)

						.queryParams("code", code)

						.queryParams("client_id",
								"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

						.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

						.queryParams("grant_type", "authorization_code")

						.queryParams("state", "verifyfjdss")

						.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

						// .queryParam("scope",
						// "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

						.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")

						.when().log().all()

						.post("https://www.googleapis.com/oauth2/v4/token").asString();

// System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);

		String accessToken = jsonPath.getString("access_token");

		System.out.println(accessToken);

		String r2 = given().contentType("application/json").

				queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)

				.when()

				.get("https://rahulshettyacademy.com/getCourse.php")

				.asString();

		System.out.println(r2);

	}

}