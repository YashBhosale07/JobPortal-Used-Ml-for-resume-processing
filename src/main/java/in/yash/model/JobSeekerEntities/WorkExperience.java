package in.yash.model.JobSeekerEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companiesWorkedAt;
    private String designation;
    private String projects;
    @OneToOne(targetEntity = JobSeeker.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private JobSeeker jobSeeker;

}
