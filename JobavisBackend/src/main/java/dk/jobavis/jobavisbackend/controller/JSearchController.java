package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import dk.jobavis.jobavisbackend.dto.JobData;
import dk.jobavis.jobavisbackend.repository.JobDetailsResultRepository;
import dk.jobavis.jobavisbackend.service.JSearchApiService;
import dk.jobavis.jobavisbackend.service.JobDBService;
import dk.jobavis.jobavisbackend.service.JobDetailsPrefetchService;
import dk.jobavis.jobavisbackend.service.JobFilterService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class JSearchController{

    private final JSearchApiService jsearchApiService;
    private final JobDBService jobDBService;
    private static final Logger logger = LoggerFactory.getLogger(JSearchController.class);
    private final JobFilterService jobFilterService;
    private final JobDetailsResultRepository jobDetailsResultRepository;
    private final JobDetailsPrefetchService jobDetailsPrefetchService;

    public JSearchController(JSearchApiService jsearchApiService, JobDBService jobDBService, JobFilterService jobFilterService, JobDetailsResultRepository jobDetailsResultRepository, JobDetailsPrefetchService jobDetailsPrefetchService){
        this.jsearchApiService = jsearchApiService;
        this.jobDBService = jobDBService;
        this.jobFilterService = jobFilterService;
        this.jobDetailsResultRepository = jobDetailsResultRepository;
        this.jobDetailsPrefetchService = jobDetailsPrefetchService;
    }

    @GetMapping("/search")
    public ResponseEntity<JSearchResponse> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(name = "num_pages",defaultValue = "1") int num_pages,
            @RequestParam(required = false,defaultValue = "dk") String country,
            @RequestParam(required = false,defaultValue = "da") String language,
            @RequestParam(name = "date_posted",defaultValue = "all") String date_posted,
            @RequestParam(name = "employment_types",defaultValue = "FULLTIME") String employment_types,
            @RequestParam(name = "job_requirements",required = false) String job_requirements,
            @RequestParam(required = false,defaultValue = "200") int radius,
            @RequestParam(name = "keywords",required = false) String keyWords,
            @RequestParam(name = "job_location",required = false,defaultValue = "") String job_location

    ){

        try{
            logger.info("Starting job search with query={}", query);
            JSearchResponse response = jsearchApiService.searchJobs(query,page,num_pages,country,language,date_posted,employment_types,job_requirements,radius);

            jobDBService.saveJobSearch(query,response);

            List<JobData> jobList = response.getData();
            List<JobData> jobListWithLocation = new ArrayList<>();

            //If the job location  from the JSON matches the user query, add to list
            //if User gives no job in their query, add regardless. The location will be filtered less optimally through query.
            for(JobData job : jobList){
                if(Objects.equals(job.getJob_location(),job_location)){
                    System.out.println(job.getJob_location());
                    jobListWithLocation.add(job);
                }
                if(job_location == ""){
                    jobListWithLocation.add(job);
                }

            }



            String combineQAndK = query;

            JSearchResponse filteredResponse = jobFilterService.tfidfFilter(jobListWithLocation, combineQAndK);

            jobDetailsPrefetchService.prefetchDetails(jobListWithLocation,country);
            return ResponseEntity.ok(filteredResponse);
        }catch (Exception e){
            logger.error("error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
