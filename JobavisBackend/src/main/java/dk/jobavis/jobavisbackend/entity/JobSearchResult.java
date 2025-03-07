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

    @Lob
    private String jsonResponse;

    public JobSearchResult(){}

    public JobSearchResult(String jsonResponse){
        this.jsonResponse = jsonResponse;
    }

}
