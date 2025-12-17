package org.ilmi.expensefulserver.security;

import org.ilmi.expensefulserver.domain.User;
import org.ilmi.expensefulserver.output.persistence.adapter.UserPersistenceAdapter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExpensefulUserDetailsService implements UserDetailsService {
    private final UserPersistenceAdapter userPersistenceAdapter;

    public ExpensefulUserDetailsService(UserPersistenceAdapter userPersistenceAdapter) {
        this.userPersistenceAdapter = userPersistenceAdapter;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userPersistenceAdapter.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new ExpensefulUserDetails(user);
    }

    public UserDetails loadByUserId(@NonNull String id) {
        User user = userPersistenceAdapter.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new ExpensefulUserDetails(user);
    }

    public @NonNull UserDetails loadUserByEmail(@NonNull String email) throws UsernameNotFoundException {
        User user = userPersistenceAdapter.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new ExpensefulUserDetails(user);
    }

    public Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // Set Default Role
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }

    public String getStringAuthorities(User user) {
        return getAuthorities(user).toString();
    }

}
