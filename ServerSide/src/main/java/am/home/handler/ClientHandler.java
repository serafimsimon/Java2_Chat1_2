package am.home.handler;

import am.home.service.MyServer;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String nickName;

    public ClientHandler(MyServer myServer, Socket socket) {

        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.nickName = "";
            long start = System.currentTimeMillis();

            new Thread(() -> {
                try {
                    authentication();
                    receive();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    myServer.sendMessageToClients(nickName + " покинул(а) чат");
                    closeConnection();
                }
            }).start();

            while(!Thread.currentThread().isInterrupted()) {
                long finish = System.currentTimeMillis();
                if((finish - start) >= 120000 && this.nickName.isEmpty() || this.nickName == null) {
                    closeConnection();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);

        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authentication() throws Exception {

        while (true) {

            String message = dis.readUTF();
            if (message.startsWith("/start")) {
                String[] arr = message.split("-", 3);
                if (arr.length != 3) {
                    throw new IllegalAccessException();
                }
                final String nick = myServer
                        .getAuthenticationService()
                        .getNickNameByLoginPassword(arr[1].trim(), arr[2].trim());

                if (nick != null) {
                    if (!myServer.nickIsBusy(nick)) {
                        send(nick + " в чате");
                        this.nickName = nick;
                        myServer.sendMessageToClients(nick + "зашел в чат");
                        myServer.subscribe(this);
                        return;
                    } else {
                        send("Ник занят");
                    }
                } else {
                    send("Неверный логин и/или пароль");

                }
            }
        }
    }


    public void send(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() throws IOException {
        while (true) {
            String message = dis.readUTF();
            if (message.startsWith("/")) {
                if (message.startsWith("/finish")) {
                    myServer.sendMessageToClients(nickName + " вышел из чата");
                    return;
                }
                if (message.startsWith("/w")) {
                    String to = message.split("-", 3)[1];
                    String msg = message.split("-", 3)[2];
                    myServer.sendMessageToClient(this, to, msg);
                }
                if (message.startsWith("/list")) {
                    myServer.onLineUsers(this);
                }
                continue;
            }
            myServer.sendMessageToClients(nickName + ": " + message);
        }
    }

    public String getNickName() {
        return nickName;
    }
}
