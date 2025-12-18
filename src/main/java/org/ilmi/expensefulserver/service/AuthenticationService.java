package org.ilmi.expensefulserver.service;

import org.ilmi.expensefulserver.domain.User;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    /**
     * Authenticates a user and returns a token if successful.
     * @param email User email
     * @param password User password
     * @return authentication token
     */
    ResponseCookie authenticate(String email, String password);

    /**
     * Registers a new user.
     * @param email User email
     * @param name User name
     * @param password User password
     * @return the registered User
     */
    User register(String email, String name, String password);

    /**
     * Logs out a user by invalidating the provided token.
     */
    ResponseCookie logout();

    User getSession(Authentication authentication);
}
