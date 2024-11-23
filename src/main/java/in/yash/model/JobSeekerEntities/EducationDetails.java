package in.yash.model.JobSeekerEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String highestQualification;
    private String institutionName;
    private String graduationYear;
    @OneToOne(targetEntity = JobSeeker.class,cascade = CascadeType.ALL)
    private JobSeeker jobSeeker;

}