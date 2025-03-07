package dk.jobavis.jobavisbackend.repository;

import dk.jobavis.jobavisbackend.entity.JobSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSearchResultRepository extends JpaRepository<JobSearchResult,Long> {
}
