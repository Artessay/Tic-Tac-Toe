// import java.awt.BorderLayout;
// import java.awt.Dimension;
// import java.awt.GridLayout;
// import java.text.NumberFormat;
// import java.util.Locale;
// import javax.swing.*;

// @SuppressWarnings("serial")
// public class Test extends JPanel {
//     private JButton reAddButtonsBtn = new JButton("Re-Add Buttons");
//     private JPanel jpFullofButtons = new JPanel(new GridLayout(0, 1));
//     private JScrollPane jsp = new JScrollPane(jpFullofButtons);
    
//     public Test() {
//         reAddButtonsBtn.addActionListener(e -> reAddButtons());
//         JPanel topPanel = new JPanel();
//         topPanel.add(reAddButtonsBtn);
        
//         jsp.setPreferredSize(new Dimension(385, 450));
        
//         int gap = 20;
//         setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
//         setLayout(new BorderLayout());
//         add(topPanel, BorderLayout.PAGE_START);
//         add(jsp, BorderLayout.CENTER);
//     }
    
//     private void reAddButtons() {
//         jpFullofButtons.removeAll();
//         int max = 100;
//         for (int i = 0; i < max; i++) {
//             String randomText = "";
//             for (int j = 0; j < 5; j++) {
//                 char c = (char) ('a' + (int) (26 * Math.random()));
//                 randomText += c;
//             }
            
//             double randomPrice = 10 + 20 * Math.random();
//             final DataVO2 data = new DataVO2(randomText, randomPrice);
            
//             String text = String.format("Text: %s%02d, Price: $%1.2f", randomText, i, randomPrice);
//             JButton button = new JButton(text);
//             button.addActionListener(e -> buttonAction(data));
//             jpFullofButtons.add(button);
//         }
//         jpFullofButtons.revalidate();
//         jpFullofButtons.repaint();
        
//     }
    
//     private void buttonAction(DataVO2 data) {
//         System.out.println("Button pressed: " + data);
//     }
    
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             JButtonsShowUp mainPanel = new JButtonsShowUp();

//             JFrame frame = new JFrame("GUI");
//             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//             frame.add(mainPanel);
//             frame.pack();
//             frame.setLocationRelativeTo(null);
//             frame.setVisible(true);
//         });
//     }

// }

// class DataVO2 {
//     private NumberFormat priceFormat = NumberFormat.getCurrencyInstance(Locale.US);
//     private String name;
//     private double price;

//     public DataVO2(String name, double price) {
//         this.name = name;
//         this.price = price;
//     }

//     public String getName() {
//         return name;
//     }

//     public double getPrice() {
//         return price;
//     }

//     @Override
//     public String toString() {
//         String priceText = priceFormat.format(price);
//         return "DataVO2 [name=" + name + ", price=" + priceText + "]";
//     }
    
// }