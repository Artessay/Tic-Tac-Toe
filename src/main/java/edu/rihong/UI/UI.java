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
        frame.setLayout(null);

        Container container = frame.getContentPane();

        // JPanel panel = new JPanel();
        // panel.setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth  = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();

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

        
        JLabel background = new JLabel(SwingUtil.createAutoAdjustIcon(new ImageIcon("img/spring.png").getImage()));
        // background.setPreferredSize(new Dimension(screenWidth, (int)(screenHeight * 0.15)));
        background.setBounds(0, 0, screenWidth, (int)(screenHeight * 0.15));
        container.add(background);

        ImageIcon face;
        if (app.userInformation.getGender() == 'M') {
            face = SwingUtil.createAutoAdjustIcon(new ImageIcon("img/default_avatar_boy.png").getImage());
        } else {
            face = SwingUtil.createAutoAdjustIcon(new ImageIcon("img/default_avatar_girl.png").getImage());
        }
        JLabel headPhoto = new JLabel(face);
        headPhoto.setBounds((int)(0.85 * screenWidth), (int)(0.15 * screenHeight), (int)(0.15 * screenWidth), (int)(0.15 * screenWidth));
        container.add(headPhoto);

        JLabel userNameLabel = new JLabel(app.userInformation.getName());
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        userNameLabel.setBounds((int)(0.85 * screenWidth), (int)(0.4 * screenHeight), (int)(0.15 * screenWidth), (int)(0.1 * screenHeight));
        container.add(userNameLabel);

        JButton buttonLogin = new JButton();
        buttonLogin.setText("Sign in");
        buttonLogin.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        buttonLogin.setBounds(screenWidth - 200, screenHeight - 150, 200, 40);
        buttonLogin.setFocusPainted(false);
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.setBackground(Color.CYAN);
        buttonLogin.addActionListener(e -> {
            new UIlogin(frame, app);
            System.out.println(app.userInformation.getName());
            userNameLabel.setText(app.userInformation.getName());
            userNameLabel.repaint();
        });
        container.add(buttonLogin);

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

        //Adding Components to the frame.
        frame.setJMenuBar(menu_bar);
        // panel.add(BorderLayout.EAST, chatPanel);
        // panel.add(BorderLayout.NORTH, background);
        // panel.add(BorderLayout.CENTER, ta);
        // frame.add(panel);
        // frame.getContentPane().add(BorderLayout.EAST, chatPanel);
        // frame.getContentPane().add(BorderLayout.NORTH, background);
        // frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
    }
}
