package am.home.service.interfaces;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService {

    private static Connection connection;
    private static PreparedStatement pr;

    public static class UserEntity {

        private int id;
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


    public void start() throws SQLException {

        System.out.println("Сервис аутентификации запущен");

    }


    public void stop() {

        System.out.println("Сервис аутентификации остановлен");
        disconnect();

    }


    public void disconnect() {
        try {
            if (pr != null) {
                pr.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public AuthenticationServiceImpl() throws SQLException {

              connection = DriverManager.getConnection("jdbc:sqlite:users.db");
              pr = connection.prepareStatement("SELECT * FROM users;");
              ResultSet rs = pr.executeQuery();

              this.userEntityList = new ArrayList<>();

            while (rs.next()) {

                userEntityList.add(new UserEntity(rs.getString("login"), rs.getString("password"), rs.getString("nickName")));

            }
    }

    public String getNickNameByLoginPassword(String login, String password) {

        for (UserEntity user : userEntityList) {
            if (user.login.equals(login) && user.password.equals(password))
                return user.nickName;
        }
        return null;
    }
}

