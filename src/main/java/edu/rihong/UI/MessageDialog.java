package edu.rihong.UI;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MessageDialog extends JFrame {
    public MessageDialog(String message) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Set Frame Position and Size
        int frameWidth = 500, frameHeight = 100;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(((int) screenSize.getWidth() - frameWidth) / 2, ((int) screenSize.getHeight() - frameHeight) / 2, frameWidth, frameHeight);

        JLabel text = new JLabel(message);
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        this.getContentPane().add(text);

        this.setVisible(true);
    }
}
