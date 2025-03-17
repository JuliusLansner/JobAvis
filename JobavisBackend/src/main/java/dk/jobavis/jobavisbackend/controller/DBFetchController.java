package dk.jobavis.jobavisbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.jobavis.jobavisbackend.dto.JDetailsResponse;
import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import dk.jobavis.jobavisbackend.entity.JobDetailsResult;
import dk.jobavis.jobavisbackend.entity.JobSearchResult;
import dk.jobavis.jobavisbackend.repository.JobDetailsResultRepository;
import dk.jobavis.jobavisbackend.repository.JobSearchResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DBFetchController {
    private final JobSearchResultRepository jobSearchResultRepository;
    private final JobDetailsResultRepository jobDetailsResultRepository;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(DBFetchController.class);

    public DBFetchController(JobSearchResultRepository jobSearchResultRepository, JobDetailsResultRepository jobDetailsResultRepository, ObjectMapper objectMapper) {
        this.jobSearchResultRepository = jobSearchResultRepository;
        this.jobDetailsResultRepository = jobDetailsResultRepository;
        this.objectMapper = objectMapper;
    }
    @GetMapping("/dbfetch/{id}")
    public ResponseEntity<?> fetchJsonFromDB(@PathVariable("id") String id){
        try{
            logger.info("DB fetch with ID: {}", id);
            JobSearchResult entity = jobSearchResultRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new RuntimeException("Error: No DB entry found with ID= "+id));

            String rawJson = entity.getJsonResponse();

            JSearchResponse parsedJson = objectMapper.readValue(rawJson,JSearchResponse.class);

            return ResponseEntity.ok(parsedJson);
        }catch(Exception e){
            logger.error("Error: {}", String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/dbfetchdetails/{job_id}")
    public ResponseEntity<?> fetchJsonDetailsFromDB(@PathVariable("job_id") String id){
        try{
            logger.info("DB fetch with ID: {}", id);
            JobDetailsResult entity = jobDetailsResultRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error: No DB entry found with ID= "+id));

            String rawJson = entity.getJsonResponse();

            JDetailsResponse parsedJson = objectMapper.readValue(rawJson,JDetailsResponse.class);

            return ResponseEntity.ok(parsedJson);
        }catch(Exception e){
            logger.error("Error: {}", String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
