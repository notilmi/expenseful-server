package org.ilmi.expensefulserver.input.web;

import jakarta.validation.Valid;
import org.ilmi.expensefulserver.input.web.data.input.LoginDTO;
import org.ilmi.expensefulserver.input.web.data.input.RegisterDTO;
import org.ilmi.expensefulserver.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginDTO request
    ) {
        ResponseCookie accessToken = authenticationService.authenticate(request.getEmail(), request.getPassword());

        // Set HTTP Headers
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.add(HttpHeaders.SET_COOKIE, accessToken.getValue());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Login successful");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterDTO request
    ) {
        authenticationService.register(request.getEmail(), request.getName(), request.getPassword());

        return ResponseEntity.ok("Registration successful");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie deleteCookie = authenticationService.logout();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, deleteCookie.getValue());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Logout successful");
    }

    

}
