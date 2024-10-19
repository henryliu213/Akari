package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary lib;
  private int index = 0;
  private List<int[]> lamps = new LinkedList<>();
  private List<ModelObserver> obs = new LinkedList<>();

  public ModelImpl(PuzzleLibrary library) {
    lib = library;
  }

  @Override
  public void addLamp(int r, int c) {
    if (r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("less than 0");
    }
    if (r >= lib.getPuzzle(index).getHeight() || c >= lib.getPuzzle(index).getWidth()) {
      throw new IndexOutOfBoundsException("too big");
    }
    if (lib.getPuzzle(index).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("not corridor called");
    }
    int[] target = new int[] {r, c};
    for (int[] a : lamps) {
      if (Arrays.equals(a, target)) {
        return;
      }
    }
    lamps.add(target);
    for (ModelObserver o : obs) {
      o.update(this);
    }
    /*
    System.out.println("new lamp added, here are my elements");

    for (int[] a : lamps){
        System.out.printf("%d, %d\n",a[0],a[1] );
    }

     */
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (r >= lib.getPuzzle(index).getHeight() || c >= lib.getPuzzle(index).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (lib.getPuzzle(index).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    int[] target = new int[] {r, c};
    int[] found = null;
    for (int[] a : lamps) {
      if (Arrays.equals(a, target)) {
        found = a;
        break;
      }
    }
    if (found != null) {
      lamps.remove(found);
      for (ModelObserver o : obs) {
        o.update(this);
      }
    }
    /*
    System.out.println("new lamp removed/maybe no change, here are my elements");

    for (int[] a : lamps){
        System.out.printf("%d, %d\n",a[0],a[1] );
    }

     */
  }

  public boolean allCorrs(int r1, int c1, int r2, int c2) {
    // assume bounds are safe in where we use this method;
    if (r1 != r2 && c1 != c2) {
      return false;
    }
    if (r1 == r2 && c1 == c2) {
      return true;
    }
    if (r1 == r2) { // same row
      int top = Math.min(c1, c2);
      int bottom = Math.max(c1, c2);
      if (top + 1 == bottom) {
        return true;
      }
      for (int i = top + 1; i < bottom; i++) {
        if (getActivePuzzle().getCellType(r1, i) != CellType.CORRIDOR) {
          return false;
        }
      }
    }
    if (c1 == c2) {
      int left = Math.min(r1, r2);
      int right = Math.max(r1, r2);
      if (left + 1 == right) {
        return true;
      }
      for (int i = left + 1; i < right; i++) {
        if (getActivePuzzle().getCellType(i, c1) != CellType.CORRIDOR) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (r >= lib.getPuzzle(index).getHeight() || c >= lib.getPuzzle(index).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (lib.getPuzzle(index).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    int[] thisloc = new int[] {r, c};
    for (int[] lamploc : lamps) {
      if (Arrays.equals(lamploc, thisloc)) {
        return true;
      }
      if (lamploc[0] != r && lamploc[1] != c) {
        continue;
      }
      if (lamploc[0] == r) { // same row
        if (allCorrs(r, c, lamploc[0], lamploc[1])) {
          return true;
        }
      } else if (lamploc[1] == c) { // same column
        if (allCorrs(r, c, lamploc[0], lamploc[1])) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (r >= lib.getPuzzle(index).getHeight() || c >= lib.getPuzzle(index).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (lib.getPuzzle(index).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    int[] thisLoc = new int[] {r, c};
    for (int[] a : lamps) {
      if (Arrays.equals(a, thisLoc)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (!this.isLamp(r, c)) {
      throw new IllegalArgumentException();
    }
    int[] thisLoc = new int[] {r, c};
    for (int[] lampLoc : lamps) {
      if (lampLoc[0] != r && lampLoc[1] != c) {
        continue;
      }
      if (Arrays.equals(thisLoc, lampLoc)) {
        continue;
      }
      if (lampLoc[0] == r) { // same row
        if (allCorrs(r, c, lampLoc[0], lampLoc[1])) {
          return true;
        }
      } else { // same column
        if (allCorrs(r, c, lampLoc[0], lampLoc[1])) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return lib.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= lib.size()) {
      throw new IndexOutOfBoundsException();
    }
    lamps.clear();
    this.index = index;
    for (ModelObserver o : obs) {
      o.update(this);
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return lib.size();
  }

  @Override
  public void resetPuzzle() {
    lamps.clear();
    for (ModelObserver o : obs) {
      o.update(this);
    }
  }

  @Override
  public boolean isSolved() {
    /*for(int[] lamp : lamps){
        if(getActivePuzzle().getCellType(lamp[0],lamp[1]) != CellType.CORRIDOR){
            throw new IllegalArgumentException("Why is it not a corridor???");
        }
        if(!isLamp(lamp[0],lamp[1])){
            throw new IllegalArgumentException("what is going on?");
        }
        if(isLampIllegal(lamp[0],lamp[1])){
            return false;
        }
    }

     */
    for (int i = 0; i < lib.getPuzzle(index).getHeight(); i++) {
      for (int j = 0; j < lib.getPuzzle(index).getWidth(); j++) {
        if (lib.getPuzzle(index).getCellType(i, j) == CellType.CORRIDOR) {
          if (!this.isLit(i, j)) {
            return false;
          }
          if (this.isLamp(i, j)) {
            if (isLampIllegal(i, j)) {
              return false;
            }
          }
        } else if (lib.getPuzzle(index).getCellType(i, j) == CellType.CLUE) {
          if (!this.isClueSatisfied(i, j)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (r >= lib.getPuzzle(index).getHeight() || c >= lib.getPuzzle(index).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (lib.getPuzzle(index).getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int n = lib.getPuzzle(index).getClue(r, c);
    int test = 0;

    if (r > 0) {
      if (getActivePuzzle().getCellType(r - 1, c) == CellType.CORRIDOR) {
        if (isLamp(r - 1, c)) {

          test++;
        }
      }
    }
    if (r < lib.getPuzzle(index).getHeight() - 1) {
      if (getActivePuzzle().getCellType(r + 1, c) == CellType.CORRIDOR) {
        if (isLamp(r + 1, c)) {

          test++;
        }
      }
    }
    if (c > 0) {
      if (getActivePuzzle().getCellType(r, c - 1) == CellType.CORRIDOR) {
        if (isLamp(r, c - 1)) {

          test++;
        }
      }
    }
    if (c < lib.getPuzzle(index).getWidth() - 1) {
      if (getActivePuzzle().getCellType(r, c + 1) == CellType.CORRIDOR) {
        if (isLamp(r, c + 1)) {

          test++;
        }
      }
    }
    return test == n;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    this.obs.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    this.obs.remove(observer);
  }
}
