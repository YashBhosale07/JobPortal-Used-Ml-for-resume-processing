package in.yash.model;

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
    @OneToOne
    @JoinColumn(name = "user_id")  // Foreign key column
    private User jobseeker_user;
}
