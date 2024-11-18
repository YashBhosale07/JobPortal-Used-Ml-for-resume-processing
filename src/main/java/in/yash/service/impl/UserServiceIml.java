package in.yash.service.impl;

import in.yash.dto.loginRequest;
import in.yash.dto.loginResponse;
import in.yash.dto.signUpRequest;
import in.yash.dto.signUpResponse;
import in.yash.exceptionHandling.UserAlreadyExistException;
import in.yash.jwtServices.JwtTokenService;
import in.yash.model.User;
import in.yash.repo.UserRepo;
import in.yash.service.UserService;
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
        User user=mapper.map(request,User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser=userRepo.save(user);
        signUpResponse response=mapper.map(savedUser,signUpResponse.class);
        response.setRole(savedUser.getRole().name());
        return response;
    }

    @Override
    public loginResponse loginUp(loginRequest request) {
        Authentication authenticate =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        loginResponse response=new loginResponse();
        if(authenticate.isAuthenticated()){
            Optional<User> user=userRepo.findByEmail(request.getEmail());
            if(user.isEmpty()){
                throw new UsernameNotFoundException("User is not present with username: "+request.getEmail());
            }
            String token=jwtTokenService.generateToken(user.get());

            response.setToken(token);
        }
        return response;
    }
}
