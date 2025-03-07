package dk.jobavis.jobavisbackend.controller;

import dk.jobavis.jobavisbackend.entity.JobSearchResult;
import dk.jobavis.jobavisbackend.service.JobDBService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JobSaveController {
    private final JobDBService jobDBService;

    public JobSaveController(JobDBService jobDBService){
        this.jobDBService = jobDBService;
    }

    @PostMapping("/save")
    public JobSearchResult saveSearchResult(@RequestBody String jsonFromClient){
        return jobDBService.saveRawJson(jsonFromClient);
    }
}
