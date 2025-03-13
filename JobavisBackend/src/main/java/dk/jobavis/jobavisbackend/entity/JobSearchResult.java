package dk.jobavis.jobavisbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class JobSearchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String query;

    @Lob
    private String jsonResponse;

    public JobSearchResult(){}

    public JobSearchResult(String jsonResponse,String query){
        this.query = query;
        this.jsonResponse = jsonResponse;
    }

}
