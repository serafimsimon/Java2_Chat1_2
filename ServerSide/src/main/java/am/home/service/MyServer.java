package am.home.service;


import am.home.handler.ClientHandler;
import am.home.service.interfaces.AuthenticationService;
import am.home.service.interfaces.AuthenticationServiceImpl;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private static final Integer PORT = 8880;
    private List<ClientHandler> handlerList;
    private AuthenticationService authenticationService;

    public MyServer() {
        System.out.println("Старт сервера");
       try(ServerSocket serverSocket = new ServerSocket(PORT)){
            authenticationService = new AuthenticationServiceImpl();
            authenticationService.start();
            handlerList = new ArrayList<>();
            while (true) {

                System.out.println("Ожидается подключение...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
               new ClientHandler(this, socket);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {

            if (authenticationService != null) {
                authenticationService.stop();
            }
        }
    }

    public synchronized boolean nickIsBusy(String nickName){
        for(ClientHandler clientHandler: handlerList) {
            if (clientHandler.getNickName().equals(nickName)){
                return true;
            }
        }
        return false;

    }

    public synchronized void sendMessageToClients(String message) {
        handlerList.forEach(clientHandler -> clientHandler.send(message));
    }


     public synchronized void sendMessageToClient(ClientHandler from, String nickTo, String message) {
        for (ClientHandler clientHandler: handlerList){
            if(clientHandler.getNickName().equals(nickTo)) {
                clientHandler.send("От " + from.getNickName() + " " + nickTo + ": " + message);
                from.send("От " + from.getNickName() + " " + nickTo + ": "+ message);
                return;
            }
        }
        from.send("Участника с ником " + nickTo + " нет в чате");
     }

     public synchronized void onLineUsers (ClientHandler clientHandler) {
         String s ="Сейчас в чате:\n";
         for (ClientHandler ch: handlerList) {
             if(ch.getNickName().equals(clientHandler.getNickName())) {
                 continue;
             }
           s = s.concat(clientHandler.getNickName() + "\n");
        }
        clientHandler.send(s);
     }


    public synchronized void subscribe(ClientHandler clientHandler) {
        handlerList.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler){
        handlerList.remove(clientHandler);
    }

    public AuthenticationService getAuthenticationService(){
        return this.authenticationService;
    }


}
