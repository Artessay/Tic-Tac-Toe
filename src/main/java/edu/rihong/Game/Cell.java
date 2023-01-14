package edu.rihong.Game;

import java.awt.*;

public class Cell {
   public static final int SIZE = 150; // cell width and height

   public static final int PADDING = SIZE / 5;
   public static final int SEED_SIZE = SIZE - PADDING * 2;
   public static final int SEED_STROKE_WIDTH = 8; // pen's stroke width

   CellState content;
   private int row, col;

   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      content = CellState.NO_SEED;
   }

   public void newGame() {
      content = CellState.NO_SEED;
   }

   public void paint(Graphics g) {
      Graphics2D graphics = (Graphics2D)g;
      graphics.setStroke(new BasicStroke(SEED_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      
      int x1 = col * SIZE + PADDING;
      int y1 = row * SIZE + PADDING;
      if (content == CellState.CROSS) {         // draw a 2-line cross
         graphics.setColor(GamePanel.COLOR_CROSS);  
         int x2 = (col + 1) * SIZE - PADDING;
         int y2 = (row + 1) * SIZE - PADDING;
         graphics.drawLine(x1, y1, x2, y2);
         graphics.drawLine(x2, y1, x1, y2);
      } else if (content == CellState.NOUGHT) { // draw a circle
         graphics.setColor(GamePanel.COLOR_NOUGHT);
         graphics.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
      }
   }
}