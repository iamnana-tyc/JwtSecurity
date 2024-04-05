package com.iamnana.DemoSpringSecurity.controller;

import com.iamnana.DemoSpringSecurity.dto.RequestResponse;
import com.iamnana.DemoSpringSecurity.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<RequestResponse> signUp(@RequestBody RequestResponse signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<RequestResponse> signIn(@RequestBody RequestResponse signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse refreshTokenRequest){
        return ResponseEntity.ok(authService.getRefreshToken(refreshTokenRequest));
    }
}
