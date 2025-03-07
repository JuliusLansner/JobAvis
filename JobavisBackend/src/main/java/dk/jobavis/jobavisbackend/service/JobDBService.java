package dk.jobavis.jobavisbackend.service;

import dk.jobavis.jobavisbackend.entity.JobSearchResult;
import dk.jobavis.jobavisbackend.repository.JobSearchResultRepository;
import org.springframework.stereotype.Service;

@Service
public class JobDBService {
    private final JobSearchResultRepository repo;

    public JobDBService(JobSearchResultRepository repo){
        this.repo = repo;
    }

    public JobSearchResult saveRawJson(String json){
        JobSearchResult result = new JobSearchResult(json);
        return repo.save(result);
    }}
