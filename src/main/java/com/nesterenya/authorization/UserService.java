package com.nesterenya.authorization;

import com.mongodb.DuplicateKeyException;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    AccountRepository accounts;

    @PostConstruct
    protected void initialize() {
        try {
            accounts.save(new Account("user", "demo", "ROLE_USER"));
            accounts.save(new Account("admin", "admin", "ROLE_ADMIN"));

        } catch (DuplicateKeyException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accounts.findByEmail(s);
        if(account == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(account);
    }

    private GrantedAuthority createAuthority(Account account) {
        return new SimpleGrantedAuthority(account.getRole());
    }

    private User createUser(Account account) {

        return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
    }
}
