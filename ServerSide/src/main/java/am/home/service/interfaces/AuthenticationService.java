package am.home.service.interfaces;

import java.sql.SQLException;

public interface AuthenticationService {
    void start() throws SQLException;
    void stop();
    String getNickNameByLoginPassword(String login, String password);

}


