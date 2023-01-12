package edu.rihong;

import edu.rihong.Model.User;
import edu.rihong.NET.Client;
import edu.rihong.NET.Server;
import edu.rihong.UI.UI;
import edu.rihong.UI.UIregister;

/**
 * Hello world!
 *
 */
public class App 
{
    public User user;

    public Client networkClient;

    public App() {
        user = new User();
        networkClient = new Client();
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello ZJUer!" );
        new Thread(new Server()).start();

        App app = new App();
        new UI(app);
        UIregister registerUI = new UIregister(app);
        // UIlogin loginUI = new UIlogin(app);
        System.out.println( "Goodbye ZJUer!" );
    }
}
