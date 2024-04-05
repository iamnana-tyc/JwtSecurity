package com.iamnana.DemoSpringSecurity.service;

import com.iamnana.DemoSpringSecurity.dto.RequestResponse;
import com.iamnana.DemoSpringSecurity.entity.OurUser;
import com.iamnana.DemoSpringSecurity.repo.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private OurUserRepository ourUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    public AuthenticationManager authenticationManager;

    public RequestResponse signUp(RequestResponse registrationRequest){
        RequestResponse response = new RequestResponse();
        try{
            OurUser ourUser = new OurUser();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUser.setRole(registrationRequest.getRole());

            OurUser userResult = ourUserRepository.save(ourUser);

            if(userResult != null && userResult.getId() > 0){
                response.setOurUsers(userResult);
                response.setMessage("User saved successfully!");
                response.setStatusCode(200);
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public RequestResponse signIn(RequestResponse signInRequest){
        RequestResponse response = new RequestResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getEmail(),signInRequest.getPassword()));
            OurUser user = ourUserRepository.findByEmail(signInRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: " + user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(),user);

            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setMessage("USER SIGNED IN SUCCESSFULLY");
            response.setExpirationTime("24hours");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public RequestResponse getRefreshToken(RequestResponse refreshTokenRegistration){
        RequestResponse response = new RequestResponse();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRegistration.getToken());
        OurUser users = ourUserRepository.findByEmail(ourEmail).orElseThrow();

        if(jwtUtils.isTokenValid(refreshTokenRegistration.getToken(),users)){
            var jwt = jwtUtils.generateToken(users);

            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRegistration.getToken());
            response.setExpirationTime("24hours");
            response.setMessage("TOKEN IS SUCCESSFULLY REFRESHED!");
        }
        //response.setStatusCode(500);
        return response;
    }
}
