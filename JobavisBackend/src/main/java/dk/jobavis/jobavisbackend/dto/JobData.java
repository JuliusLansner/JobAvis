package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JobData {

    private String job_id;
    private String job_title;
    private String employer_name;
    private String job_description;
    private String job_location;
    private String job_employment_type;

}
