package stepDefinition;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import utils.TestContext;

public class CreateIsuueWithComment {

	private TestContext context;

	public CreateIsuueWithComment(TestContext context) {
		super();
		this.context = context;
	}

	@And("I add a comment to the created issue")
	public void i_add_a_comment_to_created_issue() {
		// Construct the comment body as a plain text string
		String commentBody = "This is a comment added via API.";

		try {
			context.response = given().header("Authorization",
					"Basic a2F0ZXBxYUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbGd2bjJTdTdmWG9ZMTNnLU9OMmpjZWVScGZ0bWNfeGhkZFN0QmptLWwwd1pyQ0NMVTlHSXFJQTFPWlB4UmxPUXBXVVN6RUlvMXM3U29Oem5lOWF3YkF5UEZ3endXS0c0QWlORG9xeU1MYU53SnZFY0xkMzdfZzhrbnR2YlNvYy0tYTNSdEdHVllLQzNPQjQ4eDcwU2t3Z2dkQnQta2JqeENiVG56cnBMMlc4PTcxRTcxMEMx") // header
					.contentType("application/json").body("{\"body\":\"" + commentBody + "\"}")
					.post("/rest/api/3/issue/" + context.issueKey + "/comment");

			System.out.println("Response Body: " + context.response.getBody().asString());

			assertEquals(201, context.response.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
