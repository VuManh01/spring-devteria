package com.example.devteria.service;

import com.example.devteria.dtos.request.AuthenticationRequest;
import com.example.devteria.dtos.request.IntrospectRequest;
import com.example.devteria.dtos.response.AuthenticationResponse;
import com.example.devteria.dtos.response.IntrospectResponse;
import com.example.devteria.exception.AppException;
import com.example.devteria.exception.ErrorCode;
import com.example.devteria.model.User;
import com.example.devteria.repository.UserRepo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
//    protected final String SIGNER_KEY="4708fbaa9ac3b09928e72e34717313e5ed4875ccbcda39a9ea764b0835ce2657";

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();

    }


    public AuthenticationResponse authenticated(AuthenticationRequest request){

        User existingUser = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        log.info("User: ",existingUser);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean checkPassword = passwordEncoder.matches(request.getPassword(), existingUser.getPassword());
        if(!checkPassword) throw new AppException(ErrorCode.UNMATCH_PASSWORD);

        String token = generateToken(existingUser.getUsername());


        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

        return authenticationResponse;
    }

    public String generateToken(String username){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username) //đại diện cho user đăng nhập
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                        ))
                .claim("customClaim","Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());


        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token");
            throw new RuntimeException(e);
        }
    }
}
