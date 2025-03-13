package dk.jobavis.jobavisbackend.entity;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;

@Entity
public class JobDetailsResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String jobId;

    @Lob
    private String JsonResponse;

    public JobDetailsResult(){}
    public JobDetailsResult( String jobId, String jsonResponse) {
        this.jobId = jobId;
        this.JsonResponse = jsonResponse;
    }
}
