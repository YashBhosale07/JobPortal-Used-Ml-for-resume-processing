package in.yash.repo;

import in.yash.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepo extends JpaRepository<JobPost,Long> {
    List<JobPost> findByRecruiterId(Long recruiterId);
}
