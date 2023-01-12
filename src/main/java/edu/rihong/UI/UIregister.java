package edu.rihong.UI;

import edu.rihong.App;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UIregister extends JDialog implements ActionListener  {
    private App app;

    public UIregister(App app) {
        this.app = app;
        createUI();
    }

    public UIregister(JFrame owner, App app) {
        super(owner, true);
        this.app = app;
        createUI();
    }

    public void createUI() {
        //Creating the Frame
        // JFrame.setDefaultLookAndFeelDecorated(true);
        this.setTitle("Art Register");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(UI.icon);
        
        // Set Frame Position and Size
        int frameWidth = 800, frameHeight = 600;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(((int) screenSize.getWidth() - frameWidth) / 2, ((int) screenSize.getHeight() - frameHeight) / 2, frameWidth, frameHeight);

        // Get layout
        Container container = this.getContentPane();
        container.setLayout(null);
        container.setBackground(Color.PINK);

        // Background
        JLabel background = new JLabel(new ImageIcon("img/background.jpg"));
        background.setBounds(0, 0, frameWidth, (frameWidth*5/16));
        container.add(background);

        // account
        labelAccount  = new JLabel("<html><h2>Account </h2></html>");
        labelAccount.setForeground(Color.WHITE);
        labelAccount.setBounds((frameWidth - 400)/2 - 50, (frameHeight - 50)/2 - 25, 100, 50);
        container.add(labelAccount);
        
        inputTextAccount = new JTextField();
        inputTextAccount.setBounds((frameWidth - 400)/2 + 50, (frameHeight - 40)/2 - 20, 400, 40);
        inputTextAccount.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        container.add(inputTextAccount);

        // username
        labelUsername = new JLabel("<html><h2>Username</h2></html>");
        labelUsername.setForeground(Color.WHITE);
        labelUsername.setBounds((frameWidth - 400)/2 - 50, (frameHeight - 50)/2 + 30, 100, 50);
        container.add(labelUsername);
        
        inputTextUsername = new JTextField();
        inputTextUsername.setBounds((frameWidth - 400)/2 + 50, (frameHeight - 50)/2 + 35, 400, 40);
        inputTextUsername.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));;
        container.add(inputTextUsername);

        // gender
        labelGender = new JLabel("<html><h2>Gender  </h2></html>");
        labelGender.setForeground(Color.WHITE);
        labelGender.setBounds((frameWidth - 400)/2 - 50, (frameHeight - 50)/2 + 80, 100, 50);
        container.add(labelGender);
        
        JPanel buttonPanel = new JPanel();
        
        JRadioButton chooseBoy = new JRadioButton("boy     ", true);
        chooseBoy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        chooseBoy.setOpaque(false);
        chooseBoy.addActionListener(this);
        JRadioButton chooseGirl = new JRadioButton("girl");
        chooseGirl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        chooseGirl.setOpaque(false);
        chooseGirl.addActionListener(this);

        ButtonGroup group = new ButtonGroup();
        group.add(chooseBoy);
        group.add(chooseGirl);

        // buttonPanel.setLayout(new BorderLayout());
        // buttonPanel.add(BorderLayout.WEST, chooseBoy);
        // buttonPanel.add(BorderLayout.EAST, chooseGirl);
        buttonPanel.add(chooseBoy);
        buttonPanel.add(chooseGirl);
        buttonPanel.setOpaque(false);
        // buttonPanel.setContentAreaFilled(false);
        buttonPanel.setBounds((frameWidth - 400)/2 + 50, (frameHeight - 50)/2 + 85, 400, 40);
        container.add(buttonPanel);

        // password
        labelPassword = new JLabel("<html><h2>Password</h2></html>");
        labelPassword.setForeground(Color.WHITE);
        labelPassword.setBounds((frameWidth - 400)/2 - 50, (frameHeight - 50)/2 + 140, 100, 50);
        container.add(labelPassword);
        
        inputTextPassword = new JTextField();
        inputTextPassword.setBounds((frameWidth - 400)/2 + 50, (frameHeight - 50)/2 + 145, 400, 40);
        inputTextPassword.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));;
        container.add(inputTextPassword);

        JButton buttonLogin = new JButton();
        buttonLogin.setText("Register");
        buttonLogin.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        buttonLogin.setBounds((frameWidth - 200) / 2, frameHeight - 100, 200, 40);
        buttonLogin.setFocusPainted(false);
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.setBackground(new Color(245, 116, 180));
        buttonLogin.addActionListener(this);
        container.add(buttonLogin);

        // this.setUndecorated(true);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() instanceof JRadioButton) {
            JRadioButton radioButton = (JRadioButton)event.getSource();
            if (radioButton.getText().equals("girl")) {
                inputGender = 'F';
            } else {
                inputGender = 'M';
            }
            System.out.println("gender: " + inputGender);
        }
        else if (event.getSource() instanceof JButton) {
            System.out.println("Register");

            String account = inputTextAccount.getText().trim();
            if (account.equals("")) {
                JOptionPane.showConfirmDialog(null, "Account can't be empty", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }

            String username = inputTextUsername.getText().trim();
            if (username.equals("")) {
                JOptionPane.showConfirmDialog(null, "Username can't be empty", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }

            String password = inputTextPassword.getText().trim();
            if (password.equals("")) {
                JOptionPane.showConfirmDialog(null, "Password can't be empty", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }

            app.user.setAccount(account);
            app.user.setName(username);
            app.user.setGender(inputGender);
            app.user.setPassword(password);
            boolean ret = app.networkClient.postRegister(app.user);
            if (ret) {
                JOptionPane.showConfirmDialog(null, "Register Success", "PROMPT", JOptionPane.DEFAULT_OPTION);
                this.dispose();
            } 
            else {
                // new MessageDialog("Account or password is Wrong");
                JOptionPane.showConfirmDialog(null, "Register Failed", "ERROR", JOptionPane.DEFAULT_OPTION);
                // System.out.println("Account or password is Wrong");
            }
        }
        
    }

    char inputGender = 'M';
    JLabel labelGender;
    JLabel labelAccount;
    JLabel labelUsername;
    JLabel labelPassword;
    JTextField inputTextAccount;
    JTextField inputTextUsername;
    JTextField inputTextPassword;
}
