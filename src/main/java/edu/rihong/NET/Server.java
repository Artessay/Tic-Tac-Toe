package edu.rihong.NET;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import edu.rihong.DB.Database;
import edu.rihong.Model.User;

class HandleAClient implements Runnable {
    private Socket socket;  // A connected socket
    private Database database;

    public HandleAClient(Socket socket, Database database) {
        this.socket = socket;
        this.database = database;
    }

    public void run() {
        try {
            DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
            DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                String method = inputFromClient.readUTF();
                switch (method) {
                    case "LOGIN":
                        String account = inputFromClient.readUTF();
                        String password = inputFromClient.readUTF();
                        
                        User user = new User();
                        Boolean ret = database.login(account, password, user);
                        
                        if (ret == true) {
                            outputToClient.writeInt(Protocol.LOGIN_SUCCESS.ordinal());
                            objectOutput.writeObject(user);
                        }
                        else {
                            outputToClient.writeInt(Protocol.LOGIN_FAILED.ordinal());
                        }
                        break;
                
                    default:
                        break;
                }
            }
            
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}

public class Server extends Thread {
    // private static int clientNumber = 0;
    private Database database;
    
    public Server() {
        database = new Database();
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5842);
            System.out.println("Server Socket start at 5842");

            while (true) {
                Socket socket = serverSocket.accept();
                
                // ++clientNumber;
                System.out.println("[server] connected with " + socket.getPort());
                
                new Thread(new HandleAClient(socket, database)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
