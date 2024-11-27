    package in.yash.model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import in.yash.model.JobSeekerEntities.ApplyForJob;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.Date;
    import java.util.List;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class JobPost {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String description;
        private String requiredSkills;
        private String experienceLevel;
        private String location;
        private String salary;
        private Date postedDate;
        private String jobStatus;
        private String hrEmail;
        @ManyToOne(targetEntity = Recruiter.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        @JsonBackReference
        private Recruiter recruiter;
        @OneToMany(targetEntity = ApplyForJob.class,cascade = CascadeType.ALL,mappedBy = "jobPost")
        @JsonManagedReference
        private List<ApplyForJob>applyForJobs;

    }
