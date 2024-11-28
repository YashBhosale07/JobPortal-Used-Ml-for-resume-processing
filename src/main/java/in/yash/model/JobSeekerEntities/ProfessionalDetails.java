package in.yash.model.JobSeekerEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import in.yash.utils.JobPreferences;
import in.yash.utils.LocationPreference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double experienceYears;
    @Enumerated(EnumType.STRING)
    private JobPreferences jobPreferences;
    @Enumerated(EnumType.STRING)
    private LocationPreference locationPreference;
    @OneToOne(targetEntity = JobSeeker.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private JobSeeker jobSeeker;
}
