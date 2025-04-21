package nate.company.history_work.handle_connection_spring;

import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class LoggedUser implements HttpSessionBindingListener, Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private ActiveUserStore activeUserStore;

    @Autowired
    private UserRepository userRepository;


    public LoggedUser(String username, ActiveUserStore activeUserStore) {
        this.username = username;
        this.activeUserStore = activeUserStore;
        this.userRepository = userRepository;
    }

    public LoggedUser() {}

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<User> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (!users.contains(user.getUsername())) {
            User user1;
            if(userRepository.findByPseudo(user.getUsername()).isPresent()){
                user1 = userRepository.findByPseudo(user.getUsername()).get();
                users.add(user1);
            }

        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<User> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (users.contains(user.getUsername())) {
            if(userRepository.findByPseudo(user.getUsername()).isPresent()) {
                var user1 = userRepository.findByPseudo(user.getUsername());
                users.remove(user1);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public ActiveUserStore getActiveUserStore() {
        return activeUserStore;
    }

    // standard getter and setter
}
