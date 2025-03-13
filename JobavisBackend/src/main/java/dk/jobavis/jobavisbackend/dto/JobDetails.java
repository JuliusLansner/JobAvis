package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    private String job_apply_link;
    private Boolean job_apply_is_direct;
    private List<ApplyOption> apply_options;
    private String job_description;
    private Boolean job_is_remote;
    private String job_posted_at;
    private String job_posted_at_timestamp;
    private String job_posted_at_datetime_utc;
    private String job_location;
    private String job_city;
    private String job_state;
    private String job_country;
    private String job_latitude;
    private String job_longitude;
    private String job_benefits;
    private String job_google_link;
    private String job_salary;
    private String job_min_salary;
    private String job_max_salary;
    private String job_salary_period;
    private String job_onet_soc;
    private String job_onet_job_zone;
    private List<EmployerReviews> employer_reviews;
    private JobHighlights job_highlights;

}
