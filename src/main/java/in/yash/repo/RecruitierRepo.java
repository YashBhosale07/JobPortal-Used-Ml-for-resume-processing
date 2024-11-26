package in.yash.repo;

import in.yash.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitierRepo extends JpaRepository<Recruiter,Long> {
}
