package edu.rihong.UI;

import edu.rihong.App;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UIlogin extends JDialog implements ActionListener {
    private App app;

    public UIlogin(App app) {
        this.app = app;
        createUI();
    }

    public UIlogin(JFrame owner, App app) {
        super(owner, true);
        this.app = app;
        createUI();
    }

    public void createUI() {
        //Creating the Frame
        // JFrame.setDefaultLookAndFeelDecorated(true);
        this.setTitle("Art Login");
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
        labelAccount.setBounds((frameWidth - 400)/2 - 50, (frameHeight - 50)/2 + 20, 100, 50);
        container.add(labelAccount);
        
        inputTextAccount = new JTextField();
        inputTextAccount.setBounds((frameWidth - 400)/2 + 50, (frameHeight - 40)/2+ 20, 400, 40);
        inputTextAccount.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));;
        container.add(inputTextAccount);

        // password
        labelPassword = new JLabel("<html><h2>Password</h2></html>");
        labelPassword.setForeground(Color.WHITE);
        labelPassword.setBounds((frameWidth - 400)/2 - 50, (frameHeight - 50)/2 + 90, 100, 50);
        container.add(labelPassword);
        
        inputTextPassword = new JPasswordField();
        inputTextPassword.setBounds((frameWidth - 400)/2 + 50, (frameHeight - 50)/2 + 95, 400, 40);
        inputTextPassword.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        container.add(inputTextPassword);

        JButton buttonLogin = new JButton();
        buttonLogin.setText("Login");
        buttonLogin.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        buttonLogin.setBounds((frameWidth - 200) / 2, frameHeight - 150, 200, 40);
        buttonLogin.setFocusPainted(false);
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.setBackground(new Color(245, 116, 180));
        buttonLogin.addActionListener(this);
        container.add(buttonLogin);

        // this.setUndecorated(true);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        System.out.println("Login");

        String account = inputTextAccount.getText().trim();
        String password = new String(inputTextPassword.getPassword());

        System.out.println(account);
        System.out.println(password);

        boolean ret = app.networkClient.postLogin(account, password, app.user);
        if (ret) {
            System.out.println("Login success");
            // this.setVisible(false);
            // notifyAll();
            System.out.println("login: " + app.user.getName());
            this.dispose();
        } 
        else {
            // new MessageDialog("Account or password is Wrong");
            JOptionPane.showConfirmDialog(null, "Account or password is Wrong", "ERROR", JOptionPane.DEFAULT_OPTION);
            System.out.println("Account or password is Wrong");;
        }
    }

    JLabel labelAccount;
    JLabel labelPassword;
    JTextField inputTextAccount;
    JPasswordField inputTextPassword;
}

/*
 * 
        // close icon and minimum icon
        // JPanel button_panel = new JPanel(new GridLayout(1, 2));
        JButton button_minimum = new ButtonMinimum();
        button_minimum.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setExtendedState(JFrame.ICONIFIED);
                }
            }
        );
        // button_minimum.setBounds(frameWidth - 50*2, 0, 30, 30);
        container.add(button_minimum);

        // JButton button_close = new ButtonClose(this);
        JButton button_close = new JButton(new ImageIcon("img/close.png"));
        button_close.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
		    }
        );
        button_close.setBounds(frameWidth - 50, 0, 30, 30);
        container.add(button_close);
        // button_panel.add(button_minimum);
        // button_panel.add(button_close);
        // container.add(button_panel);
 */