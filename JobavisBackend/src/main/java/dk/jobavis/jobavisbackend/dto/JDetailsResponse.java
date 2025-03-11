package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.jobavis.jobavisbackend.controller.JDetailsController;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JDetailsResponse {
    private String status;
    private String request_id;
    private JDetailsParameters parameters;
    private List<JobDetails> data;
}
