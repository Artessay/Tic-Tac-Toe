package edu.rihong.Game;

import java.awt.*;

/**
 * The Cell class models each individual cell of the game board
 */
public class Cell {
   // Define named constants for drawing
   public static final int SIZE = 150; // cell width/height (square)

   // Symbols (cross/nought) are displayed inside a cell, with padding from border
   public static final int PADDING = SIZE / 5;
   public static final int SEED_SIZE = SIZE - PADDING * 2;
   public static final int SEED_STROKE_WIDTH = 8; // pen's stroke width

   // Define properties (package-visible)
   /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
   CellState content;
   /** Row and column of this cell */
   int row, col;

   /** Constructor to initialize this cell with the specified row and col */
   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      content = CellState.NO_SEED;
   }

   /** Reset this cell's content to EMPTY, ready for new game */
   public void newGame() {
      content = CellState.NO_SEED;
   }

   /** Paint itself on the graphics canvas, given the Graphics context */
   public void paint(Graphics g) {
      // Use Graphics2D which allows us to set the pen's stroke
      Graphics2D graphics = (Graphics2D)g;
      graphics.setStroke(new BasicStroke(SEED_STROKE_WIDTH,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      // Draw the Seed if it is not empty
      int x1 = col * SIZE + PADDING;
      int y1 = row * SIZE + PADDING;
      if (content == CellState.CROSS) {
         graphics.setColor(GamePanel.COLOR_CROSS);  // draw a 2-line cross
         int x2 = (col + 1) * SIZE - PADDING;
         int y2 = (row + 1) * SIZE - PADDING;
         graphics.drawLine(x1, y1, x2, y2);
         graphics.drawLine(x2, y1, x1, y2);
      } else if (content == CellState.NOUGHT) {  // draw a circle
         graphics.setColor(GamePanel.COLOR_NOUGHT);
         graphics.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
      }
   }
}