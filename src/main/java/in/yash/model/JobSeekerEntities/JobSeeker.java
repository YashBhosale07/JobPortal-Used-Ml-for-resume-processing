package in.yash.model.JobSeekerEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import in.yash.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    @Column(name = "resume_link")
    @Basic(fetch = FetchType.LAZY)
    private byte[] resumeLink;
    private String skills;

    @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private User jobseeker_user;

    @OneToOne(targetEntity = WorkExperience.class, mappedBy = "jobSeeker",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private WorkExperience workExperience;

    @OneToOne(targetEntity = ProfessionalDetails.class, mappedBy = "jobSeeker",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private ProfessionalDetails professionalDetails;

    @OneToOne(targetEntity = EducationDetails.class, mappedBy = "jobSeeker",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private EducationDetails educationDetails;

    @OneToMany(targetEntity = ApplyForJob.class,cascade =CascadeType.ALL,mappedBy = "jobSeeker",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ApplyForJob> applyForJob;

}
