package edu.rihong.NET;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    public Client() {
        // create connection
        try {
            // Create a socket to connect to the server
            socket = new Socket("localhost", 5842);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // create a output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean postLogin(String account, String password) {
        if (account.equals("") || password.equals("")) {
            return false;
        }

        try {
            toServer.writeUTF(account);
            toServer.writeUTF(password);
            
            int state = fromServer.readInt();
            if (state == Protocol.LOGIN_SUCCESS.ordinal()) {
                ;
            }
        } catch (IOException e) {
            // TODO: handle exception
        }

        return false;
    }
}
