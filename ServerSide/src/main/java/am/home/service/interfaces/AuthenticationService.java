package am.home.service.interfaces;

public interface AuthenticationService {
void start();
void stop();
String getNickNameByLoginPassword(String login, String password);


}


