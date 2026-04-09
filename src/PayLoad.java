
public class PayLoad {

	public static String addPlaceData() {

		return "{\n" + "  \"location\": {\n" + "    \"lat\": -39.383494,\n" + "    \"lng\": 39.427362\n" + "  },\n"
				+ "  \"accuracy\": 50,\n" + "  \"name\": \"Cristiano Ronaldo\",\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\n"
				+ "  \"address\": \"40, spanish layout, madrid 09\",\n" + "  \"types\": [\n" + "    \"shoe park\",\n"
				+ "    \"shop\"\n" + "  ],\n" + "  \"website\": \"rahulshettyacademy.com\",\n"
				+ "  \"language\": \"French-IN\"\n" + "}";

	}

	public static String getCourse() {

		return "{\n" + "\n" + "\"dashboard\": {\n" + "\n" + "\"purchaseAmount\": 910,\n" + "\n"
				+ "\"website\": \"rahulshettyacademy.com\"\n" + "\n" + "},\n" + "\n" + "\"courses\": [\n" + "\n" + "{\n"
				+ "\n" + "\"title\": \"Selenium Python\",\n" + "\n" + "\"price\": 50,\n" + "\n" + "\"copies\": 6\n"
				+ "\n" + "},\n" + "\n" + "{\n" + "\n" + "\"title\": \"Cypress\",\n" + "\n" + "\"price\": 40,\n" + "\n"
				+ "\"copies\": 4\n" + "\n" + "},\n" + "\n" + "{\n" + "\n" + "\"title\": \"RPA\",\n" + "\n"
				+ "\"price\": 45,\n" + "\n" + "\"copies\": 11\n" + "\n" + "}\n" + "\n" + "]\n" + "\n" + "}";

	}

	public static String addBook(String name,String isbn, String aisle, String author) {

		return "{\n" + "\n" + "\"name\":\""+name+"\",\n" + "\"isbn\":\"" + isbn + "\",\n" + "\"aisle\":\""
				+ aisle + "\",\n" + "\"author\":\""+author+"\"\n" + "}\n" + "";

	}

	public static String deleteBook(String id) {

		return "{\n" + " \n" + "\"ID\" : \"" + id + "\"\n" + " \n" + "} \n" + "";

	}

}
