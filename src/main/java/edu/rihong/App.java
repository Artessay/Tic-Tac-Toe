package edu.rihong;

import edu.rihong.DB.Database;
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
        // networkClient = new Client();
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello ZJUer!" );
        // new Thread(new Server()).start();

        App app = new App();
        UI gui = new UI(app);
        // UIlogin loginUI = new UIlogin(app);
        // Test ui = new Test();
        // Database database = new Database();
        // if (database.register("3200105842", "qrh", "qrh")) {
        //     System.out.println("sucess");
        // } else {
        //     System.out.println("false");
        // }
        // String [] username = new String[1];
        // System.out.println(database.login("3200105842", "qrh", username) + username[0]);
        System.out.println( "Goodbye ZJUer!" );
    }
}
