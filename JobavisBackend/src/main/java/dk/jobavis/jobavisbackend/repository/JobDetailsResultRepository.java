package dk.jobavis.jobavisbackend.repository;

import dk.jobavis.jobavisbackend.entity.JobDetailsResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDetailsResultRepository extends JpaRepository<JobDetailsResult,Long> {
}
