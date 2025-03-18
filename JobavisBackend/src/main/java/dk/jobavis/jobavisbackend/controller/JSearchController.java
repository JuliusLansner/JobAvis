package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import dk.jobavis.jobavisbackend.dto.JobData;
import dk.jobavis.jobavisbackend.service.JSearchApiService;
import dk.jobavis.jobavisbackend.service.JobDBService;
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

import java.util.List;

@RestController
@RequestMapping("/api")
public class JSearchController{

    private final JSearchApiService jsearchApiService;
    private final JobDBService jobDBService;
    private static final Logger logger = LoggerFactory.getLogger(JSearchController.class);
    private final JobFilterService jobFilterService;

    public JSearchController(JSearchApiService jsearchApiService, JobDBService jobDBService, JobFilterService jobFilterService){
        this.jsearchApiService = jsearchApiService;
        this.jobDBService = jobDBService;
        this.jobFilterService = jobFilterService;
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
            @RequestParam(required = false,defaultValue = "500") int radius,
            @RequestParam(name = "keywords",required = false) String keyWords

    ){

        try{
            logger.info("Starting job search with query={}", query);
            JSearchResponse response = jsearchApiService.searchJobs(query,page,num_pages,country,language,date_posted,employment_types,job_requirements,radius);

            jobDBService.saveJobSearch(query,response);


            List<JobData> jobList = response.getData();
            if(jobList == null || jobList.isEmpty()){
                return ResponseEntity.ok((JSearchResponse) List.of());
            }

            String combineQAndK = query +(keyWords != null ? " "+ keyWords: " ");
            JSearchResponse filteredResponse = jobFilterService.tfidfFilter(jobList, combineQAndK);


            return ResponseEntity.ok(filteredResponse);
        }catch (Exception e){
            logger.error("error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
