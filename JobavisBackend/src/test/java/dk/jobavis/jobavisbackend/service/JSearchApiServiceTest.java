package dk.jobavis.jobavisbackend.service;


import dk.jobavis.jobavisbackend.dto.JDetailsResponse;
import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
class JSearchApiServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private JSearchApiService jSearchApiService;


    private String sampleJsonResponse = "{\n" +
            "  \"status\": \"OK\",\n" +
            "  \"request_id\": \"1\",\n" +
            "  \"parameters\": {\n" +
            "    \"query\": \"developer\",\n" +
            "    \"page\": 1,\n" +
            "    \"num_pages\": 1,\n" +
            "    \"country\": \"dk\",\n" +
            "    \"language\": \"da\",\n" +
            "    \"date_posted\": \"all\"\n" +
            "  },\n" +
            "  \"data\": [\n" +
            "    { \"job_id\": \"123\", \"job_title\": \"Backend Developer\", \"employer_name\": \"Company Denmark\" }\n" +
            "  ]\n" +
            "}";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Configure the WebClient builder to return a mocked WebClient.
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        // Set properties manually for the test.
        jSearchApiService.rapidApiKey = "test-key";
        jSearchApiService.rapidApiHost = "test-host";
    }




}