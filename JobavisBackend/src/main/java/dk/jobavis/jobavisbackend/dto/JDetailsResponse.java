package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.jobavis.jobavisbackend.controller.JDetailsController;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class JDetailsResponse {
    private String status;
    private String request_id;
    private JDetailsParameters parameters;
    private List<JobDetails> data;
}
