package stepDefinition;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONObject;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.JsonReader;
import utils.TestContext;

public class CreateStoreStepDefinition {

//	Response response;
	private TestContext context;

	public CreateStoreStepDefinition(TestContext context) {
		super();
		this.context = context;
	}

	JSONObject requestObject;

	@When("I invoke stores api with post method")
	public void invokeStoresApiWithPostMethod() {
		Response response = given().log().all().contentType("application/json").body(requestObject.toString()).when()
				.post("stores");
		context.response = response;
		System.out.println("Context.response in CreateStoreDefinition:  " + context.response.asString());
	}

	@Given("create request for the method using following values")
	public void createRequest(DataTable datatable) {

		Map<String, String> reqParamsMap = datatable.asMaps().get(0);

		requestObject = new JSONObject();
		requestObject.put("name", reqParamsMap.get("name"));
		requestObject.put("address", reqParamsMap.get("address"));
		requestObject.put("city", reqParamsMap.get("city"));
		requestObject.put("state", reqParamsMap.get("state"));
		requestObject.put("zip", reqParamsMap.get("zip"));

	}

//	@Then("the response code  should be {int}")
//	public void the_response_code_should_be(Integer int1) {
//		System.out.println("Then Method");
//		Assert.assertEquals(Long.toString(int1), Long.toString(response.statusCode()));
//	}

	@Given("populate the request with json from {string}")
	public void createRequestFromjson(String fileName) {

		requestObject = JsonReader.readJsonFile(fileName);

	}

	@Then("extract the storeId")
	public void extractStoreIdFromResponse() {
		context.storeId = context.response.body().jsonPath().getInt("id");
		System.out.println("context.storeId" + context.storeId);
	}

}
