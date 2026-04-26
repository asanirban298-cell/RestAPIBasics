import io.restassured.RestAssured;
import io.restassured.config.XmlConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import POJO.InsurancePolicy;

import static io.restassured.RestAssured.*;

public class InsuranceAPIAutomation {

	@Test
	public void testInsuranceNamespaceXml() {
		// 1. Configure Rest-Assured to be Namespace Aware
		RestAssured.config = RestAssured.config().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true));

		// 2. Prepare POJO Data
		InsurancePolicy req = new InsurancePolicy();
		req.setId("POL-999");
		// ... set other fields ...

		// 3. Execute and Extract as String
		Response response = given().header("Content-Type", "text/xml").body(req).when()
				.post("https://api.majesco.com/services/policy").then().statusCode(200).extract().response();

		String xmlResponse = response.asString();

		// 4. Advanced XMLPath with Namespaces
		XmlPathConfig config = new XmlPathConfig().namespaceAware(true);
		XmlPath xp = new XmlPath(xmlResponse).using(config);

		// Use the 'declareNamespace' to make GPath readable
		// Syntax: Envelope.Body.ResponseNode
		String status = xp.get("Envelope.Body.PolicyResponse.Status");

		// Extracting attribute from a namespaced node
		String polId = xp.get("Envelope.Body.PolicyResponse.Policy.@id");

		// GPath to find Liability coverage amount
		String liability = xp.getString("**.find { it.@name == 'Liability' }");

		Assert.assertEquals(status, "SUCCESS");
		Assert.assertEquals(polId, "POL-999");
	}
}