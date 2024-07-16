package com.example.blogapp.controller;


import com.example.blogapp.config.CustomUserDetailService;
import com.example.blogapp.dto.request.JwtAuthRequest;
import com.example.blogapp.dto.response.JwtAuthResponse;
import com.example.blogapp.util.JwtTokenHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {


    private final JwtTokenHelper jwtTokenHelper;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailsService;

    public AuthController(JwtTokenHelper jwtTokenHelper, AuthenticationManager authenticationManager, CustomUserDetailService userDetailsService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());

        authenticationManager.authenticate(authenticationToken);


        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(user);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
