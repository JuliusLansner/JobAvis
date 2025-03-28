package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class JobHighlights {
    @JsonProperty("Qualifications")
    private List<String> qualifications;
    @JsonProperty("Benefits")
    private List<String> benefits;
    @JsonProperty("Responsibilities")
    private List<String> responsibilities;

}
