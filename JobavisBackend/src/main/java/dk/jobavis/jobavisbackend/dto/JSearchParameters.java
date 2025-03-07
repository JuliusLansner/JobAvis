package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSearchParameters {
    @JsonProperty("query")
    private String query;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("num_pages")
    private Integer num_pages;
    @JsonProperty("country")
    private String country;
    @JsonProperty("language")
    private String language;
    @JsonProperty("date_posted")
    private String date_posted;
    @JsonProperty("job_requirements")
    private List<String> job_requirements;
    @JsonProperty("radius")
    private Integer radius;


}
