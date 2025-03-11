package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JDetailsParameters {
    @JsonProperty("job_id")
    private String job_id;
    @JsonProperty("country")
    private String country;
}
