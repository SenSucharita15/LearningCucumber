package stepDefinition;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;

import com.google.gson.Gson;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.createIssueRequest.CreateIssueRequest;
import model.createIssueResponses.CreateIssueMetadataResponse;
import model.createIssueResponses.Issuetype;
//import model.createIssueResponses.CreateIssueResponses;
import utils.JsonReader;
import utils.TestContext;

public class CreateIssueAPIStepDefinition {

	private TestContext context;


	public CreateIssueAPIStepDefinition(TestContext context) {
		super();
		this.context = context;
	}

	// Response response;
//	String projectId;
	// String issueId;
	Gson gson = new Gson();
	CreateIssueMetadataResponse createMetadataResponse;
	
	@When("I invoke the CreateIssueMetadata API")
	public void i_invoke_the_create_issue_metadata_api() {
	    // Write code here that turns the phrase above into concrete actions
		baseURI = "https://katep.atlassian.net";
		context.response = given().header("Authorization",
				"Basic a2F0ZXBxYUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbGd2bjJTdTdmWG9ZMTNnLU9OMmpjZWVScGZ0bWNfeGhkZFN0QmptLWwwd1pyQ0NMVTlHSXFJQTFPWlB4UmxPUXBXVVN6RUlvMXM3U29Oem5lOWF3YkF5UEZ3endXS0c0QWlORG9xeU1MYU53SnZFY0xkMzdfZzhrbnR2YlNvYy0tYTNSdEdHVllLQzNPQjQ4eDcwU2t3Z2dkQnQta2JqeENiVG56cnBMMlc4PTcxRTcxMEMx")
				.contentType("application/json").get("/rest/api/3/issue/createmeta");
	}

	@Then("I extract the projectId and issueTypeId")
	public void i_extract_the_project_id_and_issue_type_id() {
		

		//Step 2 - Parse the response and store the values in CreateIssueMetadataResponse pojo class
		createMetadataResponse = context.response.as(CreateIssueMetadataResponse.class);

		//Step 3 - Extract the values from the pojo class
		
		context.projectId = createMetadataResponse.getProjects().get(0).getId();
		System.out.println("projectId : " + context.projectId);
		
		List<Issuetype> issueTypes = createMetadataResponse.getProjects().get(0).getIssuetypes();

		for (Issuetype issuetype : issueTypes) {
			
			if(issuetype.getName().equals("Story")) {
				context.issueTypeId = issuetype.getId();
			}
		}
		
		System.out.println("issueid : " + context.issueTypeId);
		 
	}

	@When("i invoke the createIssueAPI")
	public void i_invoke_the_create_issue_api() {

		// private createIssueResponses createIssueResponse;
	   
		//Read the json file
		JSONObject jsonObject = JsonReader.readJsonFile("CreateIssue.json");
		
		//Convert this Json to a pojo class
		
		CreateIssueRequest createIssueRequest = gson.fromJson(jsonObject.toString(), CreateIssueRequest.class);
		
		// set in the dynamic values
		
		createIssueRequest.getFields().getProject().setId(context.projectId);
		createIssueRequest.getFields().getIssuetype().setId(context.issueTypeId);

		// change response with context.response a it will be shared
		context.response = given().header("Authorization",
				"Basic a2F0ZXBxYUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbGd2bjJTdTdmWG9ZMTNnLU9OMmpjZWVScGZ0bWNfeGhkZFN0QmptLWwwd1pyQ0NMVTlHSXFJQTFPWlB4UmxPUXBXVVN6RUlvMXM3U29Oem5lOWF3YkF5UEZ3endXS0c0QWlORG9xeU1MYU53SnZFY0xkMzdfZzhrbnR2YlNvYy0tYTNSdEdHVllLQzNPQjQ4eDcwU2t3Z2dkQnQta2JqeENiVG56cnBMMlc4PTcxRTcxMEMx")
				.
		 		contentType("application/json").body(createIssueRequest).log().all().post("/rest/api/3/issue/");

		context.issueKey = context.response.jsonPath().getString("key");

		System.out.println("The issuekey is" + context.issueKey);
		assertEquals(201, context.response.getStatusCode());
		


	}

	@Then("verify the story id is present in response")
	public void verify_the_story_id_is_present_in_response() {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertNotNull(context.response.body().jsonPath().getString("id"));
	}

//	@Then("the response code should be {int}")
//	public void the_response_code_should_be(Integer int1) {
//		System.out.println("Then Method");
//		Assert.assertEquals(Long.toString(response.statusCode()), Long.toString(int1));
//	}

	@And("I extract the projectId and {string}")
	public void i_extract_the_project_id_and_issue_type(String issueType) {
		CreateIssueMetadataResponse createMetadataResponse = context.response.as(CreateIssueMetadataResponse.class);

		context.projectId = createMetadataResponse.getProjects().get(0).getId();

		for (Issuetype issuetype : createMetadataResponse.getProjects().get(0).getIssuetypes()) {
			if (issuetype.getName().equals(issueType)) {
				context.issueTypeId = issuetype.getId();
				System.out.println("Issue Type: " + issueType + " (ID: " + context.issueTypeId + ")");
			}
		}

	}



	
}
