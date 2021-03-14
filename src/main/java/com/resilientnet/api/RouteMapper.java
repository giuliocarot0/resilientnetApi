package com.resilientnet.api;
import com.resilientnet.authentication.AuthenticationProvider;
import com.resilientnet.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;


@RestController
public class RouteMapper {

    @Autowired
    public JwtTokenUtils jwtTokenUtils;

    @RequestMapping("/")
    public ResponseEntity<String> index() {
        String data = "Welcome to ResilientNet Interface (r-API)\n I can check your token and tell who you are at /basic/token";
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/basic/authenticate", produces = "application/json")
    public ResponseEntity <?> generateAuthenticationToken(@RequestHeader("IDP-Authorization") String auth) throws Exception{
        String username = authenticate(auth);
        final String token = jwtTokenUtils.generateToken(username);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private String authenticate(String auth) throws Exception {
        Objects.requireNonNull(auth);
        try {
            return AuthenticationProvider.authenticate(auth);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}