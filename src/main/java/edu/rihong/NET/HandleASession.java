package edu.rihong.NET;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandleASession implements Runnable {
    private Socket socketPlayer1;
    private Socket socketPlayer2;

    private DataInputStream inputStreamPlayer1;
    private DataOutputStream outputStreamPlayer1;
    private DataInputStream inputStreamPlayer2;
    private DataOutputStream outputStreamPlayer2;

    public HandleASession(Socket player1, Socket player2) {
        this.socketPlayer1 = player1;
        this.socketPlayer2 = player2;

        try {
            // get player1 socket resource
            outputStreamPlayer1 = new DataOutputStream(socketPlayer1.getOutputStream());
            inputStreamPlayer1 = new DataInputStream(socketPlayer1.getInputStream());

            // get player2 socket resource
            outputStreamPlayer2 = new DataOutputStream(socketPlayer2.getOutputStream());
            inputStreamPlayer2 = new DataInputStream(socketPlayer1.getInputStream());

            // play start
            outputStreamPlayer1.writeInt(Protocol.PLAY_START.ordinal());
            outputStreamPlayer2.writeInt(Protocol.PLAY_START.ordinal());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ;
    }
}
