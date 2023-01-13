package edu.rihong.NET;

import java.net.Socket;

public class HandleASession implements Runnable {
    private Socket player1;
    private Socket player2;

    public HandleASession(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {
        ;
    }
}
