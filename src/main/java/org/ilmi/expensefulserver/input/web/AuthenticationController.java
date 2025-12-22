package org.ilmi.expensefulserver.input.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ilmi.expensefulserver.input.web.data.input.EditProfileDTO;
import org.ilmi.expensefulserver.input.web.data.input.LoginDTO;
import org.ilmi.expensefulserver.input.web.data.input.RegisterDTO;
import org.ilmi.expensefulserver.input.web.data.output.LoginResponseDTO;
import org.ilmi.expensefulserver.input.web.data.output.UserDTO;
import org.ilmi.expensefulserver.input.web.data.output.mapper.UserDTOMapper;
import org.ilmi.expensefulserver.security.ExpensefulUserDetails;
import org.ilmi.expensefulserver.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and profile management")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserDTOMapper userDTOMapper;

    public AuthenticationController(AuthenticationService authenticationService, UserDTOMapper userDTOMapper) {
        this.authenticationService = authenticationService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginDTO request
    ) {
        ResponseCookie accessToken = authenticationService.authenticate(request.getEmail(), request.getPassword());

        // Set HTTP Headers
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.add(HttpHeaders.SET_COOKIE, accessToken.getValue());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(
                        LoginResponseDTO.builder()
                                .accessToken(accessToken.getValue())
                                .build()
                );
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(
            @RequestBody @Valid RegisterDTO request
    ) {
        var createdUser = authenticationService.register(request.getEmail(), request.getName(), request.getPassword());

        return ResponseEntity
                .ok()
                .body(userDTOMapper.toDTO(createdUser));
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

    @GetMapping("/session")
    public ResponseEntity<UserDTO> getSession(
            @AuthenticationPrincipal ExpensefulUserDetails userDetails
            ) {
        var sessionUser = authenticationService.getSession(userDetails);

        return ResponseEntity.ok(
                userDTOMapper.toDTO(sessionUser)
        );
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserDTO> editProfile(
            @AuthenticationPrincipal ExpensefulUserDetails userDetails,
            @RequestBody @Valid EditProfileDTO request
    ) {
        var updatedUser = authenticationService.editProfile(
                userDetails,
                request.getName(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                userDTOMapper.toDTO(updatedUser)
        );
    }

}
