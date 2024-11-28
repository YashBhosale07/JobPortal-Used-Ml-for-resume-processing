package in.yash.model.JobSeekerEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import in.yash.model.JobPost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyForJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = JobPost.class,cascade = CascadeType.ALL)
    @JsonBackReference
    private JobPost jobPost;

    @ManyToOne(targetEntity = JobSeeker.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private JobSeeker jobSeeker;
    private String email;
    private String contactNumber;
    private Date appliedDate;
}
