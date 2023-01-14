package edu.rihong;

import edu.rihong.Model.User;
import edu.rihong.NET.Client;
import edu.rihong.NET.Server;
import edu.rihong.UI.UI;

/**
 * Hello world!
 *
 */
public class App 
{
    public User user;

    public Client networkClient;

    public UI ui;

    public App() {
        user = new User();
        networkClient = new Client();
    }

    private void gameOver() {
        ui.repaint();
        if (user.loginState) {
            networkClient.postFortuneUpdate(user.getAccount(), user.getFortune());
        }
    }

    public void win() {
        user.increaseFortune();
        gameOver();
    }

    public void loss() {
        user.decreaseFortune();
        gameOver();
    }

    public static void main( String[] args )
    {
        // @debug
        // new Thread(new Server()).start();
        // App app2 = new App();
        // app2.ui = new UI(app2);

        System.out.println( "Hello ZJUer!" );
        
        App app = new App();
        app.ui = new UI(app);
        
        System.out.println( "Goodbye ZJUer!" );
    }
}
