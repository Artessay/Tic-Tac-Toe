package edu.rihong.UI;

import javax.swing.*;

import edu.rihong.App;
import edu.rihong.Game.GamePanel;

import java.awt.*;
import java.awt.event.*;

/**
 * User Interface
 */
public class UI {
    private static final Color THEME_BG_COLOR = new Color(206, 248, 185);

    static Image icon = new ImageIcon("img/icon.jpg").getImage();
    private App app;
    private JFrame frame;

    class windowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            app.networkClient.close();
        }
    }

    public UI(App app) {
        this.app = app;
        createUI();
    }

    public void createUI() {

        //Creating the Frame
        frame = new JFrame("Art Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setResizable(true);
        frame.setLayout(null);
        frame.addWindowListener(new windowListener());

        Container container = frame.getContentPane();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth  = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();

        
        JLabel background = new JLabel(SwingUtil.createAutoAdjustIcon(new ImageIcon("img/spring.png").getImage()));
        // background.setPreferredSize(new Dimension(screenWidth, (int)(screenHeight * 0.15)));
        background.setBounds(0, 0, screenWidth, (int)(screenHeight * 0.15));
        container.add(background);

        JLabel headPhoto = new JLabel(updateFace());
        headPhoto.setBounds((int)(0.85 * screenWidth), (int)(0.15 * screenHeight), (int)(0.15 * screenWidth), (int)(0.15 * screenWidth));
        container.add(headPhoto);

        JLabel userNameLabel = new JLabel(app.user.getName());
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        userNameLabel.setBounds((int)(0.85 * screenWidth), (int)(0.4 * screenHeight), (int)(0.15 * screenWidth), (int)(0.1 * screenHeight));
        container.add(userNameLabel);

        fortuneLabel = new JLabel("fortune: " + String.valueOf(app.user.getFortune()));
        fortuneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fortuneLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        fortuneLabel.setBounds((int)(0.85 * screenWidth), (int)(0.45 * screenHeight), (int)(0.15 * screenWidth), (int)(0.1 * screenHeight));
        container.add(fortuneLabel);

        buttonLogin = new JButton();
        buttonLogin.setText("Sign In");
        buttonLogin.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        buttonLogin.setBounds(screenWidth - 230, screenHeight - 150, 115, 40);
        buttonLogin.setFocusPainted(false);
        buttonLogin.setForeground(Color.BLACK);
        buttonLogin.setBackground(THEME_BG_COLOR);
        // buttonLogin.setOpaque(false);
        // buttonLogin.setContentAreaFilled(false);
        buttonLogin.addActionListener(e -> {
            new UIlogin(frame, app);
            headPhoto.setIcon(updateFace());
            userNameLabel.setText(app.user.getName());
            fortuneLabel.setText("fortune: " + String.valueOf(app.user.getFortune()));
            frame.repaint();
        });
        container.add(buttonLogin);

        buttonRegister = new JButton();
        buttonRegister.setText("Sign Up");
        buttonRegister.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        buttonRegister.setBounds(screenWidth - 115, screenHeight - 150, 115, 40);
        buttonRegister.setFocusPainted(false);
        buttonRegister.setForeground(Color.BLACK);
        buttonRegister.setBackground(THEME_BG_COLOR);
        // buttonRegister.setOpaque(false);
        // buttonRegister.setContentAreaFilled(false);
        buttonRegister.addActionListener(e -> {
            new UIregister(frame, app);
            headPhoto.setIcon(updateFace());
            userNameLabel.setText(app.user.getName());
            fortuneLabel.setText("fortune: " + String.valueOf(app.user.getFortune()));
            frame.repaint();
        });
        container.add(buttonRegister);

        gamePanel = new GamePanel(app);
        container.add(gamePanel);
        
        // JTextPane chatTextPane = new JTextPane();
        // JScrollPane chatPane = new JScrollPane(chatTextPane);

        //Creating the chatPanel at bottom and adding components
        JPanel chatPanel = new JPanel(); // the chatPanel is not visible in output
        
        JLabel label = new JLabel("Enter Comment");
        JTextField textField = new JTextField(10); // accepts up to 10 characters
        JButton send = new JButton("Send");
        // JButton reset = new JButton("Reset");
        
        // chatPanel.add(chatPane);
        chatPanel.add(label); // Components Added using Flow Layout
        chatPanel.add(textField);
        chatPanel.add(send);
        // chatPanel.add(reset);

        // Text Area at the Center
        // JTextArea ta = new JTextArea();

        
        //Creating the MenuBar and adding components
        JMenuBar menu_bar = new JMenuBar();

        JMenu m1 = new JMenu("FILE");
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m12 = new JMenuItem("Save as");
        JMenuItem m13 = new JMenuItem("Rank");
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);
        menu_bar.add(m1);

        JMenu m2 = new JMenu("Help");
        // m2.addActionListener(e -> {});
        menu_bar.add(m2);

        JMenu m3 = new JMenu("Account");
        JMenuItem m31 = new JMenuItem("Sign in");
        m31.addActionListener(e -> {
            new UIlogin(frame, app);
            headPhoto.setIcon(updateFace());
            userNameLabel.setText(app.user.getName());
            fortuneLabel.setText("fortune: " + String.valueOf(app.user.getFortune()));
            frame.repaint();
        });
        JMenuItem m32 = new JMenuItem("Sign up");
        m32.addActionListener(e -> {
            new UIregister(frame, app);
            headPhoto.setIcon(updateFace());
            userNameLabel.setText(app.user.getName());
            fortuneLabel.setText("fortune: " + String.valueOf(app.user.getFortune()));
            frame.repaint();
        });
        m3.add(m31);
        m3.add(m32);
        menu_bar.add(m3);
        
        frame.setJMenuBar(menu_bar);

        frame.setVisible(true);
    }

    public void repaint() {
        fortuneLabel.setText("fortune: " + String.valueOf(app.user.getFortune()));
        frame.repaint();
    }

    private ImageIcon updateFace() {
        ImageIcon face;
        if (app.user.loginState == false) {
            //avatar_anonymous.png
            face = SwingUtil.createAutoAdjustIcon(new ImageIcon("img/avatar_anonymous.png").getImage());
        } else if (app.user.getGender() == 'M') {
            face = SwingUtil.createAutoAdjustIcon(new ImageIcon("img/default_avatar_boy.png").getImage());
        } else {
            face = SwingUtil.createAutoAdjustIcon(new ImageIcon("img/default_avatar_girl.png").getImage());
        }
        return face;
    }

    private GamePanel gamePanel;
    private JLabel fortuneLabel;
    private JButton buttonLogin, buttonRegister;
}
