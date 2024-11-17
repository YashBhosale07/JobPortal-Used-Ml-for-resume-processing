package in.yash.service;

import in.yash.dto.loginRequest;
import in.yash.dto.loginResponse;
import in.yash.dto.signUpRequest;
import in.yash.dto.signUpResponse;

public interface UserService {
    signUpResponse createUser(signUpRequest request);
    loginResponse loginUp(loginRequest request);
}
