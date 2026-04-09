import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

/**
 * Demonstrates parsing and extracting data from a complex JSON structure using
 * RestAssured's JsonPath. The class prints course details, calculates totals,
 * and validates the purchase amount.
 */
public class ComplexJSONParse {

	/**
	 * Main method to execute JSON parsing and validation logic.
	 * 
	 * Steps performed: 1. Parse the JSON payload from PayLoad.getCourse(). 2.
	 * Retrieve and print the number of courses and purchase amount. 3. Print the
	 * title of the first course. 4. Print each course's title and price. 5. Find
	 * and print the number of copies sold for the "RPA" course. 6. Calculate the
	 * total price of all courses and assert it matches the purchase amount.
	 */
	@Test
	public void coursesValidations() {

		// Parse the JSON payload using JsonPath
		JsonPath jsp = new JsonPath(PayLoad.getCourse());

		// Get the number of courses
		int numOfCourses = jsp.getInt("courses.size()");
		System.out.println(numOfCourses);

		// Get the purchase amount from the dashboard
		int purchaseAmt = jsp.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmt);

		// Get and print the title of the first course
		String firstCourseTitle = jsp.getString("courses.get(0).title");
		System.out.println(firstCourseTitle);

		// Print the title and price of each course
		for (int i = 0; i < numOfCourses; i++) {
			String title = jsp.getString("courses[" + i + "].title");
			int price = jsp.getInt("courses[" + i + "].price");
			System.out.println(title + "[" + i + "] : " + price);
		}

		// Find and print the number of copies sold for the "RPA" course
		for (int i = 0; i < numOfCourses; i++) {
			if (jsp.getString("courses[" + i + "].title").equalsIgnoreCase("RPA")) {
				System.out.println("Number of copies sold for RPA course : " + jsp.getInt("courses[" + i + "].copies"));
				break;
			}
		}

		// Calculate the total price of all courses
		int totalPrice = 0;
		for (int i = 0; i < numOfCourses; i++) {
			totalPrice = totalPrice
					+ (jsp.getInt("courses[" + i + "].price") * jsp.getInt("courses[" + i + "].copies"));
		}
		System.out.println("Total price of all courses : " + totalPrice);

		// Assert that the calculated total matches the purchase amount
		Assert.assertEquals(purchaseAmt, totalPrice);
	}
}
