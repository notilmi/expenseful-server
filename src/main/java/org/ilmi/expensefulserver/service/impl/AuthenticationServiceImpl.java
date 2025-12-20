package org.ilmi.expensefulserver.service.impl;

import org.ilmi.expensefulserver.domain.User;
import org.ilmi.expensefulserver.exception.EmailAlreadyExistsException;
import org.ilmi.expensefulserver.exception.UserNotFoundException;
import org.ilmi.expensefulserver.output.persistence.adapter.UserPersistenceAdapter;
import org.ilmi.expensefulserver.security.ExpensefulUserDetails;
import org.ilmi.expensefulserver.security.helper.JwtHelper;
import org.ilmi.expensefulserver.service.AuthenticationService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserPersistenceAdapter userPersistenceAdapter;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserPersistenceAdapter userPersistenceAdapter, JwtHelper jwtHelper, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userPersistenceAdapter = userPersistenceAdapter;
        this.jwtHelper = jwtHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseCookie authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        ExpensefulUserDetails userDetails = (ExpensefulUserDetails) authentication.getPrincipal();

        if (userDetails == null) {
            throw new UserNotFoundException();
        }

        User user = userPersistenceAdapter.findById(userDetails.getId())
                .orElseThrow(UserNotFoundException::new);

        String accessToken = jwtHelper.generateAccessToken(user);

        return ResponseCookie
                .from("access_token", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(JwtHelper.ACCESS_TOKEN_VALIDITY)
                .build();
    }

    @Override
    public User register(String email, String name, String password) {

        boolean existsByEmail = userPersistenceAdapter.existsByEmail(email);

        if (existsByEmail) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));

        return userPersistenceAdapter.save(user);
    }

    @Override
    public ResponseCookie logout() {
        return ResponseCookie
                .from("access_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
    }

    @Override
    public User getSession(ExpensefulUserDetails userDetails) {
        return userPersistenceAdapter.findById(userDetails.getId())
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User editProfile(ExpensefulUserDetails userDetails, String name, String password) {
        User user = userPersistenceAdapter.findById(userDetails.getId())
                .orElseThrow(UserNotFoundException::new);

        if (name != null) {
            user.setName(name);
        }

        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }

        return userPersistenceAdapter.save(user);
    }
}
