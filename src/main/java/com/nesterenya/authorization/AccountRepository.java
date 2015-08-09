package com.nesterenya.authorization;

import com.nesterenya.modal.Ad;
import com.nesterenya.modal.Wish;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
public class AccountRepository {

    @Autowired
    private Datastore storage;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        //account.setPassword(account.getPassword());
        storage.save(account);
        return account;
    }

    public Account findByEmail(String email) {
        Query<Account> find = storage.createQuery(Account.class).field("email").equal(email);
        return find.get();
    }
}
