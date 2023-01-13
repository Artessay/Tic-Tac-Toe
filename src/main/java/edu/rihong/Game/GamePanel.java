package edu.rihong.Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.rihong.App;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class GamePanel extends JPanel {
   // Define named constants for the drawing graphics
   public static final Color COLOR_BG = Color.WHITE;
   public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
   public static final Color COLOR_CROSS = new Color(239, 105, 80);  // Red #EF6950
   public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1

   // Define game objects
   private Board board;         // the game board
   private State currentState;  // the current state of the game
   private CellState currentPlayer; // the current player
   private CellState userRole;      // the role of user
   private JLabel statusBar;    // for displaying status message
   private JButton startButton; // start button

   private App app;

   /** Constructor to setup the UI and game components */
   public GamePanel(App app) {
      this.app = app;
      currentState = State.IDLE;

      // Deal with MouseEvent
      super.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int row = mouseY / Cell.SIZE;
            int col = mouseX / Cell.SIZE;

            if (currentState == State.PLAYING) {
               if (currentPlayer == userRole) {
                  if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                        && board.cells[row][col].content == CellState.NO_SEED) {
                     // Update cells[][] and return the new game state after the move
                     currentState = board.stepGame(currentPlayer, row, col);

                     // Switch player
                     currentPlayer = (currentPlayer == CellState.CROSS) ? CellState.NOUGHT : CellState.CROSS;
                  }
               }
               
            } else if (currentState == State.DRAW 
                  || currentState == State.CROSS_WON 
                  || currentState == State.NOUGHT_WON) {
               newGame();  // restart the game
            }
            // Refresh the drawing canvas
            repaint();  // Callback paintComponent().
         }
      });

      // Setup start button
      startButton = new JButton("START");
      startButton.setFont(new Font("OCR A Extended", Font.BOLD, 24));
      startButton.setFocusPainted(false);
      startButton.setForeground(new Color(239, 105, 80));
      startButton.setBackground(COLOR_BG);
      startButton.setBorderPainted(false);
      startButton.addActionListener(e -> {
         app.networkClient.postReady(app.user.getAccount());
         currentState = State.READY;
         this.repaint();
      });

      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel();
      statusBar.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
      statusBar.setBackground(COLOR_BG_STATUS);
      statusBar.setOpaque(true);
      statusBar.setPreferredSize(new Dimension(300, 30));
      statusBar.setHorizontalAlignment(JLabel.LEFT);
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

      super.setLayout(new BorderLayout());
      super.add(statusBar, BorderLayout.SOUTH);
      super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
            // account for statusBar in height
      super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int screenWidth  = (int)screenSize.getWidth();
      int screenHeight = (int)screenSize.getHeight();
      super.setBounds(screenWidth / 2 - Board.CANVAS_WIDTH, (screenHeight - Board.CANVAS_HEIGHT) / 2, Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30);

      // Set up Game
      // initGame();
      // newGame();
   }

   public void setRole(CellState role) {
      if (role == CellState.NO_SEED) {
         System.out.println("Program Error, role should not be empty");
         return;
      }

      userRole = role;
   }

   /** Initialize the game (run once) */
   public void initGame(CellState role) {
      board = new Board();  // allocate the game-board
      currentState = State.READY;
      setRole(role);
   }

   /** Reset the game-board contents and the current-state, ready for new game */
   public void newGame() {
      for (int row = 0; row < Board.ROWS; ++row) {
         for (int col = 0; col < Board.COLS; ++col) {
            board.cells[row][col].content = CellState.NO_SEED; // all cells empty
         }
      }
      currentPlayer = CellState.CROSS;    // cross plays first
      currentState = State.PLAYING;  // ready to play
   }

   /** Custom painting codes on this JPanel */
   @Override
   public void paintComponent(Graphics g) {  // Callback via repaint()
      super.paintComponent(g);
      setBackground(COLOR_BG); // set its background color


      // Print status-bar message
      if (currentState == State.IDLE) {
         startButton.setVisible(true);
         System.out.println("Hello");
      } else if (currentState == State.READY) {
         startButton.setVisible(false);
      } else {
         startButton.setVisible(false);
         board.paint(g);  
         
         // draw status bar
         if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == CellState.CROSS) ? "X's Turn" : "O's Turn");
         } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
         } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
         } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
         }
      }
   }
}