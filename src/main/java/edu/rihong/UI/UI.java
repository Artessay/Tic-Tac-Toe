package edu.rihong.UI;

import javax.swing.*;

import edu.rihong.App;

import java.awt.*;

public class UI {
    static Image icon = new ImageIcon("img/icon.jpg").getImage();
    private App app;

    public UI(App app) {
        this.app = app;
        createUI();
    }

    public void createUI() {

        //Creating the Frame
        JFrame frame = new JFrame("Art Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        //Creating the MenuBar and adding components
        JMenuBar menu_bar = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        menu_bar.add(m1);
        menu_bar.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m12 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m12);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Comment");
        JTextField textField = new JTextField(10); // accepts up to 10 characters
        JButton send = new JButton("Send");
        // JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(textField);
        panel.add(send);
        // panel.add(reset);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.getContentPane().add(BorderLayout.NORTH, menu_bar);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
    }
}
