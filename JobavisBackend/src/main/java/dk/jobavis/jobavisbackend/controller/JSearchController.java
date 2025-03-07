package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import dk.jobavis.jobavisbackend.dto.JobData;
import dk.jobavis.jobavisbackend.service.JSearchApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class JSearchController extends JobData {

    private final JSearchApiService jsearchApiService;


    public JSearchController(JSearchApiService jsearchApiService){
        this.jsearchApiService = jsearchApiService;

    }

    @GetMapping("/search")
    public Mono<JSearchResponse> search(
            @RequestParam String query,
            @RequestParam(required = false) int page,
            @RequestParam(name = "num_pages") int num_pages,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String language,
            @RequestParam(name = "date_posted") String date_posted,
            @RequestParam(name = "employment_types") String employment_types,
            @RequestParam(name = "job_requirements",required = false) String job_requirements,
            @RequestParam(required = false) int radius
    ){
        return jsearchApiService.searchJobs(query,page,num_pages,country,language,date_posted,employment_types,job_requirements,radius);
    }
}
