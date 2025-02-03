package in.athenaeum.springbootintegrationtestsdemo.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.Assertions;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiSteps {

    private final RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;

    @Given("the Book API is running")
    public void theBookApiIsRunning() {
        System.out.println("Book API is up and running.");
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String url) {
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("I should receive a {int} status code")
    public void iShouldReceiveAStatusCode(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode().value());
    }
}
