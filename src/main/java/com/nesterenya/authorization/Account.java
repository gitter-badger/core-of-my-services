package com.nesterenya.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nesterenya.helpers.ObjectIdDeserializer;
import com.nesterenya.helpers.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;


@Entity(value = "accounts", noClassnameStored=true)
public class Account {

    //public static final String FIND_BY_EMAIL = "Account.findByEmail";
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Id
    private ObjectId id;

    @Indexed(value = IndexDirection.ASC, name="email", unique=true, dropDups=true)
    private String email;

    @JsonIgnore
    private String password;

    private String role = "ROLE_USER";

    public Account() {
    }

    public Account(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public ObjectId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
