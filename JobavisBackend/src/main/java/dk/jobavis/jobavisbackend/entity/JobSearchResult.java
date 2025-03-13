package dk.jobavis.jobavisbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Table(name = "job_search_result")
@Entity
public class JobSearchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String query;


    @Column(name = "json_response",columnDefinition = "TEXT")
    private String jsonResponse;

    public JobSearchResult(){}

    public JobSearchResult(String query,String jsonResponse){
        this.query = query;
        this.jsonResponse = jsonResponse;
    }

}
