package edu.rihong;

import edu.rihong.Model.User;
import edu.rihong.NET.Client;
import edu.rihong.NET.Server;
import edu.rihong.UI.UI;
import edu.rihong.UI.UIlogin;

/**
 * Hello world!
 *
 */
public class App 
{
    public User userInformation;

    public Client networkClient;

    public App() {
        userInformation = new User();
        networkClient = new Client();
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello ZJUer!" );
        new Thread(new Server()).start();

        App app = new App();
        new UI(app);
        // UIlogin loginUI = new UIlogin(app);
        System.out.println( "Goodbye ZJUer!" );
    }
}
