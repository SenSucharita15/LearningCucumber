package stepDefinition;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.When;
import utils.TestContext;

public class DeleteStoreStepDefinition {

	private TestContext context;

	public DeleteStoreStepDefinition(TestContext testContext) {
		this.context = testContext;
	}

	@When("I invoke stores api with delete method for single store")
	public void i_invoke_stores_api_with_delete_method_for_single_store() {
		context.response = given().delete("stores/{id}", context.storeId);
	}

}
