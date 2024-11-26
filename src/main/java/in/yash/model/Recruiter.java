package in.yash.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    private User recruiter_user;
    @OneToMany(targetEntity = JobPost.class,cascade = CascadeType.ALL,mappedBy = "recruiter",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<JobPost> jobPost;
}
