package edu.rihong.Game;

import java.awt.*;

/**
 * The Board class models the ROWS-by-COLS game board.
 */
public class Board {
   // Define named constants
   public static final int ROWS = 3;  // ROWS x COLS cells
   public static final int COLS = 3;
   // Define named constants for drawing
   public static final int CANVAS_WIDTH = Cell.SIZE * COLS;  // the drawing canvas
   public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
   public static final int GRID_WIDTH = 8;  // Grid-line's width
   public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
   public static final Color COLOR_GRID = Color.LIGHT_GRAY;  // grid lines
   public static final int Y_OFFSET = 1;  // Fine tune for better display

   // Define properties (package-visible)
   /** Composes of 2D array of ROWS-by-COLS Cell instances */
   Cell[][] cells;

   /** Constructor to initialize the game board */
   public Board() {
      initGame();
   }

   /** Initialize the game objects (run once) */
   public void initGame() {
      cells = new Cell[ROWS][COLS]; // allocate the array
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            // Allocate element of the array
            cells[row][col] = new Cell(row, col);
               // Cells are initialized in the constructor
         }
      }
   }

   /** Reset the game board, ready for new game */
   public void newGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col].newGame(); // clear the cell content
         }
      }
   }

   /**
    *  The given player makes a move on (selectedRow, selectedCol).
    *  Update cells[selectedRow][selectedCol]. Compute and return the
    *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
    */
   public State stepGame(CellState player, int selectedRow, int selectedCol) {
      // Update game board
      cells[selectedRow][selectedCol].content = player;

      // Compute and return the new game state
      if (isWon(player, selectedRow, selectedCol)) {
         return (player == CellState.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
      } else {
         // Nobody win. Check for DRAW (all cells occupied) or PLAYING.
         for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
               if (cells[row][col].content == CellState.NO_SEED) {
                  return State.PLAYING; // still have empty cells
               }
            }
         }
         return State.DRAW; // no empty cell, it's a draw
      }
   }

   public void paint(Graphics g) {
      // Draw the grid-lines
      g.setColor(COLOR_GRID);
      for (int row = 1; row < ROWS; ++row) {
         g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDHT_HALF,
               CANVAS_WIDTH - 1, GRID_WIDTH,
               GRID_WIDTH, GRID_WIDTH);
      }
      for (int col = 1; col < COLS; ++col) {
         g.fillRoundRect(Cell.SIZE * col - GRID_WIDHT_HALF, 0 + Y_OFFSET,
               GRID_WIDTH, CANVAS_HEIGHT - 1,
               GRID_WIDTH, GRID_WIDTH);
      }

      // Draw all the cells
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col].paint(g);
         }
      }
   }

   /**
    * Determine if the player with the specified CellState wins
    * @param player
    * @param selectedRow
    * @param selectedCol
    * @return
    */
   private boolean isWon(CellState player, int selectedRow, int selectedCol) {
      boolean flag;

      // row
      flag = true;
      for (int col = 0; col < COLS; ++col) {
         if (cells[selectedRow][col].content != player) {
            flag = false;
            break;
         }
      }
      if (flag) {
         return true;
      } else {
         flag = true;
      }

      // col
      for (int row = 0; row < ROWS; ++row) {
         if (cells[row][selectedCol].content != player) {
            flag = false;
            break;
         }
      }
      if (flag) {
         return true;
      } else {
         flag = true;
      }

      // check major diagonal
      for (int diagonal = 0; diagonal < ROWS; ++diagonal) {
         if (cells[diagonal][diagonal].content != player) {
            flag = false;
            break;
         }
      }
      if (flag) {
         return true;
      } else {
         flag = true;
      }

      // check subdiagonal
      for (int diagonal = 0; diagonal < ROWS; ++diagonal) {
         if (cells[diagonal][ROWS - diagonal - 1].content != player) {
            flag = false;
            break;
         }
      }

      // all checked
      return flag;
   }
}