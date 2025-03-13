package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.dto.JDetailsResponse;
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
public class JDetailsController{
    private final JSearchApiService jSearchApiService;
    private final JobDBService jobDBService;

    public JDetailsController(JSearchApiService jSearchApiService, JobDBService jobDBService){
        this.jSearchApiService = jSearchApiService;
        this.jobDBService = jobDBService;
    }



    @GetMapping("/job-details")
    public ResponseEntity<JDetailsResponse> details(
            @RequestParam(name = "job_id") String job_id,
            @RequestParam(name = "country") String country
    ){

        JDetailsResponse response = jSearchApiService.jobDetails(job_id,country);
        jobDBService.saveJobDetails(job_id,response);
        return ResponseEntity.ok(response);
    }



}
