package dk.jobavis.jobavisbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployerReviews {
    private String publisher;
    private String employer_name;
    private String score;
    private String num_stars;
    private String review_count;
    private String max_score;
    private String reviews_link;
}
