        package in.yash.service.impl;
        import in.yash.dto.loginRequest;
        import in.yash.dto.loginResponse;
        import in.yash.dto.signUpRequest;
        import in.yash.dto.signUpResponse;
        import in.yash.exceptionHandling.UserAlreadyExistException;
        import in.yash.jwtServices.JwtTokenService;
        import in.yash.model.JobSeekerEntities.EducationDetails;
        import in.yash.model.JobSeekerEntities.JobSeeker;
        import in.yash.model.JobSeekerEntities.ProfessionalDetails;
        import in.yash.model.JobSeekerEntities.WorkExperience;
        import in.yash.model.Recruiter;
        import in.yash.model.User;
        import in.yash.repo.UserRepo;
        import in.yash.service.UserService;
        import in.yash.utils.ROLE;
        import io.jsonwebtoken.JwtException;
        import org.modelmapper.ModelMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Service;

        import java.util.Optional;

        @Service
        public class UserServiceIml implements UserService {

            @Autowired
            private PasswordEncoder passwordEncoder;

            @Autowired
            private UserRepo userRepo;

            @Autowired
            private ModelMapper mapper;

            @Autowired
            private AuthenticationManager authenticationManager;

            @Autowired
            private JwtTokenService jwtTokenService;


            @Override
            public signUpResponse createUser(signUpRequest request) {
                Optional<User>alreadyExitsUser =userRepo.findByEmail(request.getEmail());
                if(alreadyExitsUser.isPresent()){
                    throw new UserAlreadyExistException("User is already present with email: "+request.getEmail());
                }
                User user = mapper.map(request, User.class);
                user.setPassword(passwordEncoder.encode(request.getPassword()));

                // Only create JobSeeker if the role is JOBSEEKER
                if (user.getRole() == ROLE.JOBSEEKER) {
                    //JobSeeker
                    JobSeeker jobSeeker = new JobSeeker();
                    jobSeeker.setResumeLink(request.getJobSeeker().getResumeLink());
                    jobSeeker.setSkills(request.getJobSeeker().getSkills());


                    //WorkExperience
                    WorkExperience workExperience=new WorkExperience();
                    workExperience.setJobSeeker(jobSeeker);
                    workExperience.setCompaniesWorkedAt(request.getWorkExperience().getCompaniesWorkedAt());
                    workExperience.setDesignation(request.getWorkExperience().getDesignation());
                    workExperience.setProjects(request.getWorkExperience().getProjects());

                    //ProfessionalDeatils
                    ProfessionalDetails professionalDetails=new ProfessionalDetails();
                    professionalDetails.setJobSeeker(jobSeeker);
                    professionalDetails.setExperienceYears(request.getProfessionalDetails().getExperienceYears());
                    professionalDetails.setJobPreferences(request.getProfessionalDetails().getJobPreferences());
                    professionalDetails.setLocationPreference(request.getProfessionalDetails().getLocationPreference());

                    //EducationDetails
                    EducationDetails educationDetails=new EducationDetails();
                    educationDetails.setJobSeeker(jobSeeker);
                    educationDetails.setHighestQualification(request.getEducationDetails().getHighestQualification());
                    educationDetails.setInstitutionName(request.getEducationDetails().getInstitutionName());
                    educationDetails.setGraduationYear(request.getEducationDetails().getGraduationYear());

                    jobSeeker.setProfessionalDetails(professionalDetails);
                    jobSeeker.setWorkExperience(workExperience);
                    jobSeeker.setEducationDetails(educationDetails);
                    jobSeeker.setJobseeker_user(user);
                    user.setJobseeker_user(jobSeeker);  // Set JobSeeker reference in User
                    user.setRecruiter(null);  // Make sure Recruiter is null
                }

                // Only create Recruiter if the role is RECRUITER
                if (user.getRole() == ROLE.RECRUITER) {
                    Recruiter recruiter = new Recruiter();
                    recruiter.setCompanyName(request.getRecruiter().getCompanyName());
                    recruiter.setRecruiter_user(user);  // Set the user reference in Recruiter
                    user.setRecruiter(recruiter);  // Set Recruiter reference in User
                    user.setJobseeker_user(null);  // Make sure JobSeeker is null
                }

                // Save the user entity
                User savedUser = userRepo.save(user);
                signUpResponse response=mapper.map(savedUser,signUpResponse.class);
                response.setRole(savedUser.getRole().name());
                return response;
            }

            @Override
            public loginResponse loginUp(loginRequest request) {
                Authentication authenticate =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
                loginResponse response=new loginResponse();
                if(!authenticate.isAuthenticated()){
                    throw new JwtException("something went wrong");
                }
                Optional<User> user=userRepo.findByEmail(request.getEmail());
                if(user.isEmpty()){
                    throw new UsernameNotFoundException("User is not present with username: "+request.getEmail());
                }
                String accessToken=jwtTokenService.generateAccessToken(user.get());
                String refreshToken=jwtTokenService.generateRefreshToken(user.get());
                return new loginResponse(accessToken,refreshToken);
            }

            public loginResponse generateNewAcessToken(String refreshToken){
                Long id=jwtTokenService.extractId(refreshToken);
                Optional<User>user=userRepo.findById(id);
                if(user.isEmpty()){
                    throw new UsernameNotFoundException("User not found ");
                }
                String newAcessToken=jwtTokenService.generateAccessToken(user.get());
                return new loginResponse(newAcessToken,refreshToken);
            }
        }
