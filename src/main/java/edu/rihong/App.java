package edu.rihong;

import edu.rihong.DB.Database;
import edu.rihong.UI.UI;
import edu.rihong.UI.UIlogin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello ZJUer!" );
        // UI gui = new UI();
        // UIlogin loginUI = new UIlogin();
        // Test ui = new Test();
        Database database = new Database();
        if (database.register("3200105842", "qrh", "qrh")) {
            System.out.println("sucess");
        } else {
            System.out.println("false");
        }
        String [] username = null;
        System.out.println(database.login("3200105842", "qrh", username));
        System.out.println( "Goodbye ZJUer!" );
    }
}
