import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DynamicJSONTest {

	String id;

	@Test(dataProvider = "BooksData")
	public void addBook(String name, String isbn, String aisle, String author) throws IOException {

		RestAssured.baseURI = ("http://216.10.245.166");

		// Adding the same books from external JSON Books.json file
		String response = given().header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "//Books.json")))).when()
				.post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = ReusableMethods.rawToJson(response);
		System.out.println(js.getString("Msg"));
		id = js.getString("ID");
		System.out.println(id);

		// Deleting book
		response = given().header("Content-Type", "application/json").body(PayLoad.deleteBook(id)).when()
				.post("Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response().asString();

		js = ReusableMethods.rawToJson(response);
		System.out.println(js.getString("msg"));

		// Adding book from DataProvide = "BooksData"
		response = given().header("Content-Type", "application/json").body(PayLoad.addBook(name, isbn, aisle, author))
				.when().post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

		js = ReusableMethods.rawToJson(response);
		System.out.println(js.getString("Msg"));
		id = js.getString("ID");
		System.out.println(id);

		// Deleting book
		response = given().header("Content-Type", "application/json").body(PayLoad.deleteBook(id)).when()
				.post("Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response().asString();

		js = ReusableMethods.rawToJson(response);
		System.out.println(js.getString("msg"));

	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {

		return new Object[][] { { "Sei Samay", "Sunil", "1859", "Sunil Ganguly" },
				{ "Glimpses of World History", "Nehru", "1901", "Jawaharlal Nehru" } };

	}

}
