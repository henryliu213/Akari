package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI
    PuzzleLibrary lib = new PuzzleLibraryImpl();
    Puzzle puz1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puz2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puz3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puz4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puz5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    lib.addPuzzle(puz1);
    lib.addPuzzle(puz2);
    lib.addPuzzle(puz3);
    lib.addPuzzle(puz4);
    lib.addPuzzle(puz5);

    Puzzle puzOnlyOne = new PuzzleImpl(new int[][] {{6}});
    lib.addPuzzle(puzOnlyOne);

    Puzzle puzOnlyOneClueFail = new PuzzleImpl(new int[][] {{1}});
    lib.addPuzzle(puzOnlyOneClueFail);

    Puzzle puzOnlyOneCluePass = new PuzzleImpl(new int[][] {{0}});
    lib.addPuzzle(puzOnlyOneCluePass);

    Puzzle puz5x3 = new PuzzleImpl(new int[][] {{5, 6, 6, 6, 6}, {6, 6, 3, 6, 6}, {6, 6, 6, 6, 6}});
    lib.addPuzzle(puz5x3);

    Model myMod = new ModelImpl(lib);
    AlternateMvcController myControl = new ControllerImpl(myMod);
    BigView bv = new BigView(myMod, myControl);
    Scene scene = new Scene(bv.render());
    myMod.addObserver(
        new ModelObserver() {
          public void update(Model model) {
            stage.setScene(new Scene(bv.render()));
            stage.show();
          }
        });
    stage.setScene(scene);
    stage.show();
  }
}
