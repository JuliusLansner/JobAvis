package dk.jobavis.jobavisbackend.service;

import dk.jobavis.jobavisbackend.dto.JDetailsParameters;
import dk.jobavis.jobavisbackend.dto.JDetailsResponse;
import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class JSearchApiService {

    private final WebClient webClient;


    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;



    public JSearchApiService(
            WebClient.Builder webClientBuilder,
            @Value("${rapidapi.base.url}") String baseUrl
    ){
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();

    }

    public Mono<JSearchResponse> searchJobs(String query, int page, int num_pages, String country,String language, String date_posted, String employment_types, String job_requirements, int radius){
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("query",query)
                        .queryParam("page",page)
                        .queryParam("num_pages",num_pages)
                        .queryParam("country",country)
                        .queryParam("language",language)
                        .queryParam("date_posted", date_posted)
                        .queryParam("employment_types",employment_types)
                        .queryParamIfPresent("job_requirements",Optional.ofNullable(job_requirements))
                        .queryParamIfPresent("radius",Optional.ofNullable(radius))


                        .build())
                .header("x-rapidapi-key",rapidApiKey)
                .header("x-rapidapi-host",rapidApiHost)
                .retrieve()
                .bodyToMono(JSearchResponse.class);
    }


    public Mono<JDetailsResponse> jobDetails(String job_id, String country){
        System.out.println("JOBID: "+job_id);
        System.out.println("COUNTRY: "+country);
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/job-details")
                        .queryParam("job_id",job_id)
                        .queryParam("country",country)
                        .build())
                .header("x-rapidapi-key",rapidApiKey)
                .header("x-rapidapi-host",rapidApiHost)
                .retrieve()
                .bodyToMono(JDetailsResponse.class);
    }
}
