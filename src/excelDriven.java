import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class excelDriven {

	@Test
	public void addBook() throws IOException {

		String sheetName = "LibraryAPIPayload";
		String testCaseName = "AddBook";

		DataDriven dd = new DataDriven();
		ArrayList<String> data = dd.getExcelData(sheetName, testCaseName);

		HashMap<String, Object> map = new HashMap<>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));

		RestAssured.baseURI = ("http://216.10.245.166");

		// Adding the books from HashMap
		String response = given().header("Content-Type", "application/json").body(map).when()
				.post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = ReusableMethods.rawToJson(response);
		System.out.println(js.getString("Msg"));
		String id = js.getString("ID");
		System.out.println(id);

		// Deleting book
		response = given().header("Content-Type", "application/json").body(PayLoad.deleteBook(id)).when()
				.post("Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();

		js = ReusableMethods.rawToJson(response);
		System.out.println(js.getString("msg"));

	}

}
