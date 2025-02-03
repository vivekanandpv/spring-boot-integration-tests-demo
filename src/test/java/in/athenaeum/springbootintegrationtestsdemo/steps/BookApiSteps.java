package in.athenaeum.springbootintegrationtestsdemo.steps;

import static org.assertj.core.api.Assertions.assertThat;

import in.athenaeum.springbootintegrationtestsdemo.models.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        loginRequest.put("password", "testpassword");

        ResponseEntity<Map> loginResponse = restTemplate.postForEntity("/auth/login", loginRequest, Map.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).containsKey("token");

        jwtToken = loginResponse.getBody().get("token").toString();

        // Seed test data into H2
        seedTestData();
    }

    private void seedTestData() {
        Map<String, String> book1 = Map.of("title", "Effective Java", "author", "Joshua Bloch");
        Map<String, String> book2 = Map.of("title", "Clean Code", "author", "Robert C. Martin");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.postForEntity("/api/v1/books", new HttpEntity<>(book1, headers), Void.class);
        restTemplate.postForEntity("/api/v1/books", new HttpEntity<>(book2, headers), Void.class);
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
