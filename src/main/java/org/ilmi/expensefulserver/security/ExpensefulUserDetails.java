package org.ilmi.expensefulserver.security;

import lombok.Getter;
import org.ilmi.expensefulserver.domain.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ExpensefulUserDetails implements UserDetails {
    @Getter
    @NonNull
    private final String id;
    private final String username;
    private final String password;
    @Getter
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public ExpensefulUserDetails(@NonNull String id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public ExpensefulUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @NonNull String getUsername() {
        return username;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

}
