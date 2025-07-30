package com.luna.identity_service.controller;

import com.luna.identity_service.dto.request.ApiResponse;
import com.luna.identity_service.dto.request.AuthenticationRequest;
import com.luna.identity_service.dto.request.IntrospectRequest;
import com.luna.identity_service.dto.response.AuthenticationResponse;
import com.luna.identity_service.dto.response.IntrospectResponse;
import com.luna.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
