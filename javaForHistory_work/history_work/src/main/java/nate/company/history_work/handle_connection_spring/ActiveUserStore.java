package nate.company.history_work.handle_connection_spring;

import nate.company.history_work.siteTools.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
source for this package :

https://www.baeldung.com/spring-security-track-logged-in-users

 */
@Service
public class ActiveUserStore {

    public List<User> users;

    public ActiveUserStore() {
        users = new ArrayList<User>();
    }

    @Bean
    public ActiveUserStore generateActiveUserStore(){
        return new ActiveUserStore();
    }

    // standard getter and setter


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        Objects.requireNonNull(users);
        this.users = users;
    }
}