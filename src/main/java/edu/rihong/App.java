package edu.rihong;

import edu.rihong.DB.Database;
import edu.rihong.NET.Client;
import edu.rihong.UI.UI;
import edu.rihong.UI.UIlogin;

/**
 * Hello world!
 *
 */
public class App 
{
    boolean loginState;

    public Client networkClient;

    public App() {
        loginState = false;
        networkClient = new Client();
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello ZJUer!" );

        App app = new App();
        // UI gui = new UI();
        UIlogin loginUI = new UIlogin(app);
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
