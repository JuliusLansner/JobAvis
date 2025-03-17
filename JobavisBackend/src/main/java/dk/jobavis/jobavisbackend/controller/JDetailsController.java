package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.dto.JDetailsResponse;
import dk.jobavis.jobavisbackend.service.JSearchApiService;
import dk.jobavis.jobavisbackend.service.JobDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api")
public class JDetailsController{
    private final JSearchApiService jSearchApiService;
    private final JobDBService jobDBService;
    private static final Logger logger = LoggerFactory.getLogger(JDetailsController.class);
    public JDetailsController(JSearchApiService jSearchApiService, JobDBService jobDBService){
        this.jSearchApiService = jSearchApiService;
        this.jobDBService = jobDBService;
    }



    @GetMapping("/job-details")
    public ResponseEntity<JDetailsResponse> details(
            @RequestParam(name = "job_id") String jobId,
            @RequestParam(name = "country",defaultValue = "dk") String country
    ){

        try {
            logger.info("Starting job details search with ID={}", jobId);
            JDetailsResponse response = jSearchApiService.jobDetails(jobId,country);
            jobDBService.saveJobDetails(jobId,response);
            System.out.println("JOBID++: "+jobId+" RESPONSE++: "+response);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }



}
