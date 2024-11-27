package in.yash.repo;

import in.yash.model.JobSeekerEntities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepo extends JpaRepository<JobSeeker,Integer> {
}
