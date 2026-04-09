import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.CreateOrderResponse;
import POJO.Login;
import POJO.LoginResponse;
import POJO.OrderDetails;
import POJO.Orders;

public class EcomAPITests {

	public static void main(String[] args) {

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		// Login to the Ecommerce Application and get the Auth token and userId

		Login lg = new Login();
		lg.setUserEmail("asanirban298@gmail.com");
		lg.setUserPassword("sA2!091959");

		RequestSpecification req = given().log().all().spec(reqSpec).body(lg);

		LoginResponse loginRes = req.when().spec(req).post("api/ecom/auth/login").then().extract().response()
				.as(LoginResponse.class);
		String token = loginRes.getToken();
		String userId = loginRes.getUserId();
		System.out.println(token);
		System.out.println(userId);

		// Adding Product

		reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		req = given().spec(reqSpec).formParam("productName", "JordanAir").formParam("productCategory", "fashion")
				.formParam("productSubCategory", "shoes").formParam("productPrice", "55000")
				.formParam("productDescription", "Nike Originals").formParam("productFor", "men")
				.multiPart("productImage",
						new File("/Users/anirbansarkar/Desktop/Screenshot 2026-04-07 at 9.08.29 PM.png"))
				.formParam("productAddedBy", userId);

		String res = req.when().post("api/ecom/product/add-product").then().extract().response().asString();

		JsonPath resJson = ReusableMethods.rawToJson(res);
		String productId = resJson.getString("productId");
		System.out.println(productId);

		// Creating Order

		reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
				.addHeader("Authorization", token).build();

		OrderDetails od = new OrderDetails();
		od.setCountry("India");
		od.setProductOrderedId(productId);

		List<OrderDetails> orrderDetailsList = new ArrayList<OrderDetails>();
		orrderDetailsList.add(od);

		Orders orders = new Orders();
		orders.setOrders(orrderDetailsList);

		req = given().log().all().spec(reqSpec).body(orders);

		CreateOrderResponse cor = req.when().post("api/ecom/order/create-order").then().extract().response()
				.as(CreateOrderResponse.class);

		String[] expectedProductIdList = { productId };

		List<String> orderIdList = cor.getOrders();
		List<String> productIdList = cor.getProductOrderId();

		System.out.println(productIdList);
		Assert.assertTrue(productIdList.equals(Arrays.asList(expectedProductIdList)));

		// View Order Details

		reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		req = given().log().all().spec(reqSpec).queryParam("id", orderIdList.get(0));

		res = req.when().get("api/ecom/order/get-orders-details").then().extract().response().asString();

		System.out.println(res);

		// Deleting Product

		reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		req = given().log().all().spec(reqSpec).pathParam("productId", productId);
		res = req.when().delete("api/ecom/product/delete-product/{productId}").then().extract().response().asString();
		resJson = ReusableMethods.rawToJson(res);
		String message = resJson.getString("message");
		Assert.assertTrue(message.equalsIgnoreCase("Product Deleted Successfully"));
		System.out.println(message);

	}

}
