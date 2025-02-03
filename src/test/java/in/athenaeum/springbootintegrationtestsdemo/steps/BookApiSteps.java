package in.athenaeum.springbootintegrationtestsdemo.steps;
import static org.assertj.core.api.Assertions.assertThat;
import in.athenaeum.springbootintegrationtestsdemo.models.Book;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookApiSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    private String jwtToken;
    private ResponseEntity<List<Book>> response;
    
    @Given("I have a valid JWT token")
    public void i_have_a_valid_jwt_token() {
        // Login to get token
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "testuser");
        loginRequest.put("password", "password");
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity("/api/v1/auth/login", loginRequest, Map.class);
        jwtToken = loginResponse.getBody().get("token").toString();
    }
    
    @When("I make a GET request to {string}")
    public void i_make_a_get_request_to(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Book>>() {});
    }
    
    @Then("the response status should be {int}")
    public void the_response_status_should_be(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }
    
    @Then("the response should contain at least one book")
    public void the_response_should_contain_at_least_one_book() {
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody().get(0).getBookId()).isEqualTo(1); // Ensure first book has an id of 1
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("Compilers: Principles, Techniques, and Tools"); // Ensure first book has a title
    }
}