package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class JSearchResponse {

    private String status;
    private String request_id;
    private JSearchParameters parameters;
    private List<JobData> data;

}
