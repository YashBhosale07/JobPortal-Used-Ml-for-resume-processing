        package in.yash.service.impl;
        import in.yash.dto.*;
        import in.yash.exceptionHandling.ResumeFailedToSaveException;
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
        import in.yash.utils.ImageUtils;
        import in.yash.utils.JobPreferences;
        import in.yash.utils.LocationPreference;
        import in.yash.utils.ROLE;
        import io.jsonwebtoken.JwtException;
        import org.modelmapper.ModelMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.BadCredentialsException;
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
                Optional<UserDTO> userDTO=userRepo.findByEmail(request.getEmail());
                if(userDTO.isPresent()){
                    throw new UserAlreadyExistException("User is already present ");
                }
                User user = mapper.map(request, User.class);
                user.setPassword(passwordEncoder.encode(request.getPassword()));

                // Only create JobSeeker if the role is JOBSEEKER
                if (user.getRole() == ROLE.JOBSEEKER) {
                    //JobSeeker
                    JobSeeker jobSeeker = new JobSeeker();
                    try{
                        jobSeeker.setResumeLink(ImageUtils.compressImage(request.getResumeFile().getBytes()));
                    }catch (Exception e){
                        throw new ResumeFailedToSaveException("Failed to save resume file");
                    }
                    jobSeeker.setSkills(request.getSkills());

                    //WorkExperience
                    WorkExperience workExperience=new WorkExperience();
                    workExperience.setJobSeeker(jobSeeker);
                    workExperience.setCompaniesWorkedAt(request.getCompaniesWorkedAt());
                    workExperience.setDesignation(request.getDesignation());
                    workExperience.setProjects(request.getProjects());

                    //ProfessionalDeatils
                    ProfessionalDetails professionalDetails=new ProfessionalDetails();
                    professionalDetails.setJobSeeker(jobSeeker);
                    professionalDetails.setExperienceYears(request.getExperienceYears());
                    professionalDetails.setJobPreferences(JobPreferences.valueOf(request.getJobPreferences().toUpperCase()));
                    professionalDetails.setLocationPreference(LocationPreference.valueOf(request.getLocationPreference().toUpperCase()));

                    //EducationDetails
                    EducationDetails educationDetails=new EducationDetails();
                    educationDetails.setJobSeeker(jobSeeker);
                    educationDetails.setHighestQualification(request.getHighestQualification());
                    educationDetails.setInstitutionName(request.getInstitutionName());
                    educationDetails.setGraduationYear(request.getGraduationYear());

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
                    recruiter.setCompanyName(request.getCompanyName());
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

            public loginResponse loginUp(loginRequest request) {
                try {
                    Authentication authenticate = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                    );

                    loginResponse response = new loginResponse();
                    if (!authenticate.isAuthenticated()) {
                        throw new JwtException("Authentication failed");
                    }

                    Optional<UserDTO> dto = userRepo.findByEmail(request.getEmail());
                    if (dto.isEmpty()) {
                        throw new UsernameNotFoundException("User not found with email: " + request.getEmail());
                    }
                    User user=mapper.map(dto,User.class);

                    String accessToken = jwtTokenService.generateAccessToken(user);
                    String refreshToken = jwtTokenService.generateRefreshToken(user);

                    return new loginResponse(accessToken, refreshToken);

                } catch (Exception e) {
                    e.printStackTrace();  // Logs the stack trace to help identify the error
                    System.out.println("Error occurred during login: " + e.getMessage());
                    return new loginResponse("error", "error");
                }
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
