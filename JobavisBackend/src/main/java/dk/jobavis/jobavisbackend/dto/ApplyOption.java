package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class ApplyOption {
    private String publisher;
    private String apply_link;
    private Boolean is_direct;
}
