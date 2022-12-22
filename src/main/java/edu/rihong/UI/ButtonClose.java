package edu.rihong.UI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.*;

public class ButtonClose extends JButton implements ActionListener {
    private static ImageIcon icon = new ImageIcon("img/close.png");
    
    private JFrame frame_;

    public ButtonClose(JFrame frame) {
        super(icon);
        frame_ = frame;
    }

    public void actionPerformed(ActionEvent e) {
        frame_.dispatchEvent(new WindowEvent(frame_, WindowEvent.WINDOW_CLOSING));
    }
}
