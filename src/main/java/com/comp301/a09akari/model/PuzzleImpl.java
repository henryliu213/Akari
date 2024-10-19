package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;

  public PuzzleImpl(int[][] board) {
    for (int[] i : board) {
      for (int j : i) {
        if ((j != 0) && (j != 1) && (j != 2) && (j != 3) && (j != 4) && (j != 5) && (j != 6)) {
          throw new IllegalArgumentException();
        }
      }
    }
    if (board.length == 0) {
      throw new IllegalArgumentException();
    }
    if (board[0].length == 0) {
      throw new IllegalArgumentException();
    }
    this.board = board;
    // Your constructor code here
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 || c < 0 || r >= getHeight() || c >= getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    int num = board[r][c];
    if (num <= 4) {
      return CellType.CLUE;
    }
    if (num == 5) {
      return CellType.WALL;
    }
    if (num == 6) {
      return CellType.CORRIDOR;
    }
    return null;
  }

  @Override
  public int getClue(int r, int c) {

    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    return board[r][c];
  }
}
