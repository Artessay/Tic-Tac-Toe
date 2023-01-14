package edu.rihong.NET;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import edu.rihong.DB.Database;

public class HandleASession implements Runnable {
    private Socket socketPlayer1;
    private Socket socketPlayer2;

    private DataInputStream inputStreamPlayer1;
    private DataOutputStream outputStreamPlayer1;
    private DataInputStream inputStreamPlayer2;
    private DataOutputStream outputStreamPlayer2;

    private Database database;

    public HandleASession(Socket player1, Socket player2, Database database) {
        this.socketPlayer1 = player1;
        this.socketPlayer2 = player2;
        this.database = database;

        try {
            // get player1 socket resource
            outputStreamPlayer1 = new DataOutputStream(socketPlayer1.getOutputStream());
            inputStreamPlayer1 = new DataInputStream(socketPlayer1.getInputStream());

            // get player2 socket resource
            outputStreamPlayer2 = new DataOutputStream(socketPlayer2.getOutputStream());
            inputStreamPlayer2 = new DataInputStream(socketPlayer2.getInputStream());

            // play start
            outputStreamPlayer1.writeInt(Protocol.PLAY_START.ordinal());
            outputStreamPlayer1.writeUTF("PLAYER1");
            outputStreamPlayer2.writeInt(Protocol.PLAY_START.ordinal());
            outputStreamPlayer2.writeUTF("PLAYER2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            int row, col;
            String method;
            String account;
            int fortune;

            DataInputStream temperatureInput;
            DataOutputStream temperatureOutput;
            while (true) {
                method = inputStreamPlayer1.readUTF();
                switch (method) {
                    case "PLAY":
                        row = inputStreamPlayer1.readInt();
                        col = inputStreamPlayer1.readInt();
        
                        System.out.println("[server] read from player1 (" + row + ", " + col + ")");
        
                        outputStreamPlayer2.writeInt(row);
                        outputStreamPlayer2.writeInt(col);
                        
                        System.out.println("[server] write to player2 (" + row + ", " + col + ")");        
                        break;
                
                    case "FORTUNE":
                        account = inputStreamPlayer1.readUTF();
                        fortune = inputStreamPlayer1.readInt();
                        database.updateFortune(account, fortune);
                        break;
                    
                    case "OVER":
                        inputStreamPlayer2.readUTF();

                        // swap input stream
                        temperatureInput = inputStreamPlayer1;
                        inputStreamPlayer1 = inputStreamPlayer2;
                        inputStreamPlayer2 = temperatureInput;

                        // swap output stream
                        temperatureOutput = outputStreamPlayer1;
                        outputStreamPlayer1 = outputStreamPlayer2;
                        outputStreamPlayer2 = temperatureOutput;
                        
                        continue;
                        
                    default:
                        System.out.println("[server] Error, method " + method + " get");
                        break;
                }

                method = inputStreamPlayer2.readUTF();
                switch (method) {
                    case "PLAY":
                        row = inputStreamPlayer2.readInt();
                        col = inputStreamPlayer2.readInt();
                        
                        System.out.println("[server] read from player2 (" + row + ", " + col + ")");

                        outputStreamPlayer1.writeInt(row);
                        outputStreamPlayer1.writeInt(col);
                        
                        System.out.println("[server] write to player1 (" + row + ", " + col + ")");      
                        break;
                
                    case "FORTUNE":
                        account = inputStreamPlayer2.readUTF();
                        fortune = inputStreamPlayer2.readInt();
                        database.updateFortune(account, fortune);
                        break;
                        
                    case "OVER":
                        inputStreamPlayer1.readUTF();

                        // swap input stream
                        temperatureInput = inputStreamPlayer1;
                        inputStreamPlayer1 = inputStreamPlayer2;
                        inputStreamPlayer2 = temperatureInput;

                        // swap output stream
                        temperatureOutput = outputStreamPlayer1;
                        outputStreamPlayer1 = outputStreamPlayer2;
                        outputStreamPlayer2 = temperatureOutput;

                        continue;

                    default:
                        System.out.println("[server] Error, method " + method + " get");
                        break;
                }
            }
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }
}
