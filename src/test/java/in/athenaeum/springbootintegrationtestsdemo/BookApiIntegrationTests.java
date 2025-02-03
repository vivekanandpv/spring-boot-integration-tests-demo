package in.athenaeum.springbootintegrationtestsdemo;

import in.athenaeum.springbootintegrationtestsdemo.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookApiIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private String jwtToken;

    @BeforeEach
    public void setUp() {
        jwtToken = obtainJwtToken();
    }

    private String obtainJwtToken() {
        // Create login request payload
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "testuser");
        loginRequest.put("password", "password");

        // Send request to authentication endpoint
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/v1/auth/login", loginRequest, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("token");

        return response.getBody().get("token").toString();
    }

    @Test
    public void testGetBooksWithJwtToken() {
        // Set Authorization header with JWT token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Send GET request to /api/v1/books
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "/api/v1/books",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Book>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();  // Ensure books are returned
        assertThat(response.getBody().get(0).getBookId()).isEqualTo(1); // Ensure first book has an id of 1
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("Compilers: Principles, Techniques, and Tools"); // Ensure first book has a title
    }
}
