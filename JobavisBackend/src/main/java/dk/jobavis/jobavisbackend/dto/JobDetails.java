package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDetails {
    private String job_id;
    private String job_title;
    private String employer_name;
    private String employer_logo;
    private String employer_website;
    private String job_publisher;
    private String job_employment_type;
    private List<String> job_employment_types;
    private String job_apply_link;
    private boolean job_apply_is_direct;
    private List<String> apply_options;



}
