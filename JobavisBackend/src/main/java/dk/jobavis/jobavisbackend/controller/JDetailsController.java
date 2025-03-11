package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.dto.JDetailsResponse;
import dk.jobavis.jobavisbackend.service.JSearchApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api")
public class JDetailsController{
    private final JSearchApiService jSearchApiService;


    public JDetailsController(JSearchApiService jSearchApiService){
        this.jSearchApiService = jSearchApiService;

    }



    @GetMapping("/job-details")
    public Mono<JDetailsResponse> details(
            @RequestParam(name = "job_id") String job_id,
            @RequestParam(name = "country") String country
    ){
        System.out.println("JOBID: "+job_id);
        System.out.println("COUNTRY: "+country);
        return jSearchApiService.jobDetails(job_id,country);
    }



}
