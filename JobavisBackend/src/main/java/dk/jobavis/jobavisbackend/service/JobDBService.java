package dk.jobavis.jobavisbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.jobavis.jobavisbackend.dto.JobDetails;
import dk.jobavis.jobavisbackend.entity.JobDetailsResult;
import dk.jobavis.jobavisbackend.entity.JobSearchResult;
import dk.jobavis.jobavisbackend.repository.JobDetailsResultRepository;
import dk.jobavis.jobavisbackend.repository.JobSearchResultRepository;
import org.springframework.stereotype.Service;

@Service
public class JobDBService {
    private final JobSearchResultRepository jobSearchResultRepository;
    private final JobDetailsResultRepository jobDetailsResultRepository;
    private final ObjectMapper objectMapper;

    public JobDBService(JobSearchResultRepository jobSearchResultRepository,JobDetailsResultRepository jobDetailsResultRepository,ObjectMapper objectMapper) {
        this.jobSearchResultRepository = jobSearchResultRepository;
        this. jobDetailsResultRepository = jobDetailsResultRepository;
        this.objectMapper = objectMapper;
    }
    public JobSearchResult saveJobSearch(String query, Object jsonResponse){
        try{
            String json = objectMapper.writeValueAsString(jsonResponse);
            JobSearchResult entity = new JobSearchResult(query, json);
            JobSearchResult saved = jobSearchResultRepository.save(entity);
            return saved;

        }catch(Exception e){
            throw new RuntimeException("Failed: Converting search response to JSON failed");
        }
    }
    public JobDetailsResult saveJobDetails(String jobId,Object jsonResponse){
        try{
            String json = objectMapper.writeValueAsString(jsonResponse);
            JobDetailsResult entity = new JobDetailsResult(jobId,json);
            JobDetailsResult saved = jobDetailsResultRepository.save(entity);
            return saved;
            } catch(Exception e){
            throw new RuntimeException("Failed: Converting details response to JSON failed");
        }
    }

}
