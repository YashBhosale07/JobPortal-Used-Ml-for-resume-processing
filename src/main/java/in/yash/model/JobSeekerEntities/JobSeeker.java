package in.yash.model.JobSeekerEntities;

import in.yash.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String resumeLink;
    private String skills;
    @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    private User jobseeker_user;
    @OneToOne(targetEntity = WorkExperience.class, mappedBy = "jobSeeker",cascade = CascadeType.ALL)
    private WorkExperience workExperience;
    @OneToOne(targetEntity = ProfessionalDetails.class, mappedBy = "jobSeeker",cascade = CascadeType.ALL)
    private ProfessionalDetails professionalDetails;
    @OneToOne(targetEntity = EducationDetails.class, mappedBy = "jobSeeker",cascade = CascadeType.ALL)
    private EducationDetails educationDetails;

}
