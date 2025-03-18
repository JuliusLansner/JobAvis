package dk.jobavis.jobavisbackend.util;

import dk.jobavis.jobavisbackend.dto.JobData;
import lombok.Data;

@Data
public class JobDataScore {
    private final JobData jobData;
    private final double score;

    public JobDataScore(JobData jobData, double score) {
        this.jobData = jobData;
        this.score = score;
    }
}
