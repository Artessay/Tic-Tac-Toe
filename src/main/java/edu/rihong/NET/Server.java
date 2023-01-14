package edu.rihong.NET;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import edu.rihong.DB.Database;
import edu.rihong.Model.User;

class HandleAClient implements Runnable {
    private Socket socket;  // A connected socket
    private Database database;
    private HashMap<String, Socket> socketMap;
    private JTextArea textAreaLog;

    private static Lock lock = new ReentrantLock();
    private static int playerNumber = 0;
    private static Socket waitPlayer = null;

    public HandleAClient(Socket socket, Database database, HashMap<String, Socket> socketMap, JTextArea taLog) {
        this.socket = socket;
        this.database = database;
        this.socketMap = socketMap;
        this.textAreaLog = taLog;
    }

    public void run() {
        // try-resource
        // try (
        //     DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
        //     DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
        //     ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
        //     ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
        // ) {
        try {
            DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
            DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            LabelWhile:
            while (true) {
                String method = inputFromClient.readUTF();
                User user;
                String account;
                switch (method) {
                    case "LOGIN":
                        account = inputFromClient.readUTF();
                        String password = inputFromClient.readUTF();
                        
                        user = new User();

                        if (database.login(account, password, user)) {
                            socketMap.put(user.getName(), socket);
                            outputToClient.writeInt(Protocol.LOGIN_SUCCESS.ordinal());
                            objectOutput.writeObject(user);
                        } else {
                            outputToClient.writeInt(Protocol.LOGIN_FAILED.ordinal());
                        }
                        break;

                    case "REGISTER":
                        user = (User)objectInput.readObject();

                        if (database.register(user)) {
                            socketMap.put(user.getName(), socket);
                            outputToClient.writeInt(Protocol.REGISTER_SUCCESS.ordinal());
                        } else {
                            outputToClient.writeInt(Protocol.REGISTER_FAILED.ordinal());
                        }
                        break;
                    
                    case "READY":
                        account = inputFromClient.readUTF();
                        Boolean play;
                        Socket opponent = null;
                        
                        lock.lock();
                        try {
                            ++playerNumber;
                            if (playerNumber % 2 == 0) {
                                play = true;
                                opponent = waitPlayer;
                                waitPlayer = null;
                            } else {
                                play = false;
                                waitPlayer = socket;
                            }
                        } finally {
                            lock.unlock();
                        }

                        if (play) {
                            SwingUtilities.invokeLater(() -> textAreaLog.append(new Date() + ": [server] two players are ready, game starts\n"));
                            new Thread(new HandleASession(opponent, socket, database)).start();
                        } else {
                            SwingUtilities.invokeLater(() -> textAreaLog.append(new Date() + ": [server] one player is waiting\n"));
                            outputToClient.writeInt(Protocol.WAIT_PLAYER.ordinal());
                        }
                        break LabelWhile;
                        // break;

                    case "CLOSE":
                        break LabelWhile;

                    default:
                        System.out.println("[server] Unrecogized method: " + method);
                        break;
                }
            }
            
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }
}

public class Server extends Thread {
    public static int clientNumber = 0;
    private Database database;
    private HashMap<String, Socket> socketMap;
    
    public Server() {
        database = new Database();
        socketMap = new HashMap<>();

        // UI
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Text Area at the Center
        taLog = new JTextArea();
        JScrollPane scrollPanel = new JScrollPane(taLog);
        scrollPanel.setPreferredSize(new Dimension(450, 200));
        
        frame.getContentPane().add(scrollPanel);

        frame.pack();
        frame.setLocationRelativeTo(null); // center the application window
        frame.setVisible(true);
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5842);
            System.out.println("[server] Socket start at 5842");
            SwingUtilities.invokeLater(() -> taLog.append(new Date() + ": [server] Socket start at 5842\n"));

            while (true) {
                Socket socket = serverSocket.accept();
                
                ++clientNumber;
                System.out.println("[server] connected with " + socket.getPort());
                SwingUtilities.invokeLater(() -> taLog.append(new Date() + ": [server] connected with " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "\n"));
                
                new Thread(new HandleAClient(socket, database, socketMap, taLog)).start();
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
    
    public JFrame frame;
    JTextArea taLog;
}
