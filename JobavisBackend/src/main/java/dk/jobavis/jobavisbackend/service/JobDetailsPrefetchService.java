package dk.jobavis.jobavisbackend.service;

import dk.jobavis.jobavisbackend.controller.JSearchController;
import dk.jobavis.jobavisbackend.dto.JobData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDetailsPrefetchService {

    private final JSearchApiService jSearchApiService;
    private static final Logger logger = LoggerFactory.getLogger(JobDetailsPrefetchService.class);
    public JobDetailsPrefetchService(JSearchApiService jSearchApiService) {
        this.jSearchApiService = jSearchApiService;
    }

    @Async
    public void prefetchDetails(List<JobData> jobList,String country){
        for(JobData jobData : jobList){
            try {
                logger.info("getting data: {}", jobData.getJob_id());
                jSearchApiService.jobDetails(jobData.getJob_id(),country);
            }catch(Exception e){
                logger.error("Error: {}", String.valueOf(e));
            }

        }
    }
}
