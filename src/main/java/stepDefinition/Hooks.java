package stepDefinition;

//import org.junit.After;
import static io.restassured.RestAssured.given;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.TestContext;

public class Hooks {

	private TestContext context;

	public Hooks(TestContext testContext) {
		this.context = testContext;
	}

	@Before
	public void setup() {

		System.out.println("In before scenario method");
	}

	@After
	public void tearDown() {
		System.out.println("In After scenario method");
	}

//@BeforeStep
	// not used
	public void beforeStep() {
	}

	@Before("@ViewSingleStoreDetails")
	public void callBeforeSingleStoreAPI() {
		System.out.println("In before @ViewSingleStoreDetails scenario method");
	}

	@After("@Stores")
	public void deleteStoreAfterEachScenario() {
		System.out.println("In After Stores scenario :context store id" + context.storeId);
		given().log().all().delete("stores/{id}", context.storeId).then().log().all().statusCode(200);
		// Assert.assertEquals(200, context.response.statusCode());
	}

}
