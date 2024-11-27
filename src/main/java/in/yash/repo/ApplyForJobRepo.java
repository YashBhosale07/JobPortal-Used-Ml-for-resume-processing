package in.yash.repo;

import in.yash.model.JobSeekerEntities.ApplyForJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplyForJobRepo extends JpaRepository<ApplyForJob,Long> {
    Optional<List<ApplyForJob>> findByJobPostId(Long id);
}
