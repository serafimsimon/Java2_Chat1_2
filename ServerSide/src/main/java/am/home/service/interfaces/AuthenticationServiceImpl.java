package am.home.service.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AuthenticationServiceImpl implements AuthenticationService {

    private class UserEntity {
        private String login;
        private String password;
        private String nickName;

        public UserEntity(String login, String password, String nickName) {
            this.login = login;
            this.password = password;
            this.nickName = nickName;
        }
    }
    private List<UserEntity> userEntityList;

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");

    }


    public AuthenticationServiceImpl() {
        this.userEntityList = new ArrayList<>();
        userEntityList.add(new UserEntity("A", "A", "A"));
        userEntityList.add(new UserEntity("B", "B", "B"));
        userEntityList.add(new UserEntity("C", "C", "C"));
    }


    @Override
    public String getNickNameByLoginPassword(String login, String password) {
        for (UserEntity entity: userEntityList){
            if(entity.login.equals(login) && entity.password.equals(password))
                return entity.nickName;
                   }
        return null;

}

}



