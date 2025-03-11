package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyOption {
    private String publisher;
    private String apply_link;
    private Boolean is_direct;
}
