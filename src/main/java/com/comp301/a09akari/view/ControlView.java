package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ControlView implements FXComponent {
  Model mod;
  AlternateMvcController controller;

  public ControlView(Model m, AlternateMvcController controller) {
    this.mod = m;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Pane p = new HBox();
    Label lab =
        new Label(
            String.valueOf(mod.getActivePuzzleIndex() + 1)
                + " of "
                + String.valueOf(mod.getPuzzleLibrarySize()));
    p.getChildren().add(lab);
    Button prev = new Button("Previous");
    prev.setOnAction(
        actionEvent -> {
          // controller.clickResetPuzzle();
          controller.clickPrevPuzzle();
        });

    p.getChildren().add(prev);
    Button next = new Button("Next");
    next.setOnAction(
        actionEvent -> {
          // controller.clickResetPuzzle();
          controller.clickNextPuzzle();
        });
    p.getChildren().add(next);

    Button random = new Button("Random");
    random.setOnAction(
        actionEvent -> {
          controller.clickResetPuzzle();
          controller.clickRandPuzzle();
        });
    p.getChildren().add(random);

    Button reset = new Button("Reset");
    reset.setOnAction(
        actionEvent -> {
          controller.clickResetPuzzle();
        });
    p.getChildren().add(reset);
    return p;
  }
}
