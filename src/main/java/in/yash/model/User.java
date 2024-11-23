package in.yash.model;

import in.yash.model.JobSeekerEntities.JobSeeker;
import in.yash.utils.ROLE;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "app_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(value=EnumType.STRING)
    private ROLE role;
    @OneToOne(mappedBy = "recruiter_user", targetEntity = Recruiter.class,cascade = CascadeType.ALL)
    private Recruiter recruiter;

    @OneToOne(mappedBy = "jobseeker_user", targetEntity = JobSeeker.class,cascade = CascadeType.ALL)
    private JobSeeker jobseeker_user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
