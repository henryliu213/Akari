package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import com.comp301.a09akari.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements AlternateMvcController {
  private Model mod;

  public ControllerImpl(Model model) {
    this.mod = model;
  }

  @Override
  public void clickNextPuzzle() {
    if (mod.getActivePuzzleIndex() + 1 >= mod.getPuzzleLibrarySize()) {
      return;
    } else {
      mod.resetPuzzle();
      mod.setActivePuzzleIndex(mod.getActivePuzzleIndex() + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    if (mod.getActivePuzzleIndex() == 0) {
      return;
    } else {
      mod.resetPuzzle();
      mod.setActivePuzzleIndex(mod.getActivePuzzleIndex() - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    Random rand = new Random();
    int randomNum = rand.nextInt((mod.getPuzzleLibrarySize()) + 1);
    if (randomNum != mod.getActivePuzzleIndex()) {
      mod.resetPuzzle();
    }
    mod.setActivePuzzleIndex(randomNum);
  }

  @Override
  public void clickResetPuzzle() {
    mod.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (mod.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      return;
    }
    if (mod.isLamp(r, c)) {
      mod.removeLamp(r, c);
    } else {
      mod.addLamp(r, c);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return mod.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return mod.isLamp(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return mod.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return mod.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.mod.getActivePuzzle();
  }
}
