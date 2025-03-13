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
    public JobSearchResult saveJobSearch(String query,Object searchResponse){
        try{
            String json = objectMapper.writeValueAsString(searchResponse);
            JobSearchResult entity = new JobSearchResult(query,json);
            return jobSearchResultRepository.save(entity);
        }catch(Exception e){
            throw new RuntimeException("Failed: Converting search response to JSON failed");
        }
    }
    public JobDetailsResult saveJobDetails(String jobId,Object detailsResponse){
        try{
            String json = objectMapper.writeValueAsString(detailsResponse);
            JobDetailsResult entity = new JobDetailsResult(jobId,json);
            return jobDetailsResultRepository.save(entity);
            } catch(Exception e){
            throw new RuntimeException("Failed: Converting details response to JSON failed");
        }
    }
}
