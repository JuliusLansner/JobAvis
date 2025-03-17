package dk.jobavis.jobavisbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Data
@Table(name = "job_details_result")
@Entity
public class JobDetailsResult {
    @Id
    public String jobId;


    @Column(name = "json_response",columnDefinition = "TEXT")
    private String jsonResponse;

    public JobDetailsResult(){}

    public JobDetailsResult(String jobId,String jsonResponse) {
        this.jobId = jobId;
        this.jsonResponse = jsonResponse;
    }
}
