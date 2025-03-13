package dk.jobavis.jobavisbackend.service;
import dk.jobavis.jobavisbackend.dto.JDetailsResponse;
import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Optional;

@Service
public class JSearchApiService {

    private final RestTemplate restTemplate;


    @Value("${rapidapi.key}")
    String rapidApiKey;

    @Value("${rapidapi.host}")
    String rapidApiHost;


    String baseUrl = "https://jsearch.p.rapidapi.com";




    public JSearchApiService() {
        this.restTemplate = new RestTemplate();
    }


    public JSearchResponse searchJobs(String query, int page, int num_pages, String country,String language, String date_posted, String employment_types, String job_requirements, int radius) {

        // Build the URL with query parameters
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/search")
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("num_pages", num_pages)
                .queryParam("country", country)
                .queryParam("language", language)
                .queryParam("date_posted", date_posted)
                .queryParam("employment_types", employment_types)
                .queryParamIfPresent("job_requirements", Optional.ofNullable(job_requirements))
                .queryParamIfPresent("radius", Optional.of(radius))
                .build()
                .toUriString();


        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApiKey);
        headers.set("x-rapidapi-host", rapidApiHost);

        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<JSearchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JSearchResponse.class
        );

        return response.getBody();
    }


    public JDetailsResponse jobDetails(String job_id, String country) {

        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/job-details")
                .queryParam("job_id", job_id)
                .queryParam("country", country)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApiKey);
        headers.set("x-rapidapi-host", rapidApiHost);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JDetailsResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JDetailsResponse.class
        );

        return response.getBody();
    }
}
