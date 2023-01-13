package edu.rihong.NET;

import java.io.*;
import java.net.Socket;

import edu.rihong.Game.CellState;
import edu.rihong.Game.GamePanel;
import edu.rihong.Game.State;
import edu.rihong.Model.User;

public class Client {
    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Client() {
        // create connection
        try {
            // Create a socket to connect to the server
            socket = new Socket("localhost", 5842);

            // create a output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException ex) {
            System.out.println("[client] create socket failed");
            ex.printStackTrace();
        }
    }

    public void finalize() {
        try {
            fromServer.close();
            toServer.close();
            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean postRegister(User user) {
        if (user.getAccount().equals("") || user.getName().equals("") || user.getPassword().equals("")) {
            return false;
        }

        try {
            // System.out.println("[client] Begin Transfer");
            toServer.writeUTF("REGISTER");
            objectOutputStream.writeObject(user);
            // System.out.println("[client] End Transfer");
            
            int state = fromServer.readInt();
            if (state == Protocol.REGISTER_SUCCESS.ordinal()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean postLogin(String account, String password, User user) {
        if (account.equals("") || password.equals("")) {
            return false;
        }

        try {
            // System.out.println("[client] Begin Transfer");
            toServer.writeUTF("LOGIN");
            toServer.writeUTF(account);
            toServer.writeUTF(password);
            // System.out.println("[client] End Transfer");
            
            int state = fromServer.readInt();
            if (state == Protocol.LOGIN_SUCCESS.ordinal()) {
                User readUser = (User)objectInputStream.readObject();
                
                user.deepCopy(readUser);
                
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Program Error, class not found");
            e.printStackTrace();
        }

        return false;
    }

    public void postReady(String account) {
        try {
            toServer.writeUTF("READY");
            toServer.writeUTF(account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getStart(GamePanel g) {
        int state;
        try {
            state = fromServer.readInt();
            
            while (state == Protocol.WAIT_PLAYER.ordinal()) {
                state = fromServer.readInt();
            }
            if (state != Protocol.PLAY_START.ordinal()) {
                System.out.println("Program Error, get start should be PLAY_START");
                return false;
            }
            
            String role = fromServer.readUTF();
            // System.out.println(role);
            if (role.equals("PLAYER1")) {
                g.initGame(CellState.CROSS);
            } else if (role.equals("PLAYER2")) {
                g.initGame(CellState.NOUGHT);
            } else {
                System.out.println("Program Error, role is " + role);
                return false;
            }
            
            g.newGame();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private boolean waitMove = true;

    public void setWaitMove() {
        waitMove = true;
    }

    public void resetWaitMove() {
        waitMove = false;
    }

    private void waitMouseAction() {
        while (waitMove) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Auto-generated catch block
                e.printStackTrace();
            }
        }

        waitMove = true;
    }

    public void playControl(GamePanel g) {
        waitMove = true;

        new Thread(() -> {
            while (g.getCurrentState() == State.PLAYING) {
                System.out.println(g.getUserRole());
                if (g.getUserRole() == CellState.CROSS) {
                    // wait player cross to move
                    waitMouseAction();

                    // wait player nought to move
                    getLocation(g);
                    g.stepGame(CellState.NOUGHT);
                } else if (g.getUserRole() == CellState.NOUGHT) {
                    // wait player cross to move
                    getLocation(g);
                    g.stepGame(CellState.CROSS);
                    
                    // wait player cross to move
                    waitMouseAction();
                } else {
                    System.out.println("Program Error in playControl, current player wrong");
                    break;
                }
            }
            
        }).start();
    }

    public void sendLocation(int row, int col) {
        try {
            toServer.writeInt(row);
            toServer.writeInt(col);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void getLocation(int[] pos) {
        try {
            if (pos.length != 2) {
                System.out.println("Program Error, transfer location");
            }
            pos[0] = fromServer.readInt();
            pos[1] = fromServer.readInt();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void getLocation(GamePanel g) {
        try {
            g.selectedRow = fromServer.readInt();
            g.selectedCol = fromServer.readInt();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
