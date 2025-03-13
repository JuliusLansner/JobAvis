package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import dk.jobavis.jobavisbackend.service.JSearchApiService;
import dk.jobavis.jobavisbackend.service.JobDBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class JSearchController{

    private final JSearchApiService jsearchApiService;
    private final JobDBService jobDBService;


    public JSearchController(JSearchApiService jsearchApiService, JobDBService jobDBService){
        this.jsearchApiService = jsearchApiService;
        this.jobDBService = jobDBService;
    }

    @GetMapping("/search")
    public ResponseEntity<JSearchResponse> search(
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
        JSearchResponse response = jsearchApiService.searchJobs(query,page,num_pages,country,language,date_posted,employment_types,job_requirements,radius);
        jobDBService.saveJobSearch(query,response);
        return ResponseEntity.ok(response);
    }
}
