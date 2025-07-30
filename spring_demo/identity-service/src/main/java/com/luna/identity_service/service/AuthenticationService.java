package com.luna.identity_service.service;

import com.luna.identity_service.dto.request.AuthenticationRequest;
import com.luna.identity_service.dto.request.IntrospectRequest;
import com.luna.identity_service.dto.response.AuthenticationResponse;
import com.luna.identity_service.dto.response.IntrospectResponse;
import com.luna.identity_service.exception.AppException;
import com.luna.identity_service.exception.ErrorCode;
import com.luna.identity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Value("${jwt.signerKey}")
    protected static String SIGN_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(String username){
        //JWS yeu câu 2 param là header và payload
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512); //thuật toán để mã hoá
        //Các data trong body của Payload gọi là claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username) //đại diện cho user đăng nhâp
                .issuer("lunadev.com") //xac dinh token dc issue tu ai. Thong thuong la domain
                .issueTime(new Date()) //thoi diem hien tai
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() //Het han sau 1h
                ))
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); //payload nhan 1 JsonObject nen phai ep kieu

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        //Ky token
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes())); //Can 1 chuoi 32byte Secret
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot Create Token", e);
            throw new RuntimeException(e);
        }
    }

    //Xac thuc token
    public IntrospectResponse introspect(IntrospectRequest request)
            throws ParseException, JOSEException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier); //tra ve true neu verifier thanh cong (noi dung token dung) va false neu nguoc lai
        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }
}
