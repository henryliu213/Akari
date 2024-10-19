package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class PuzzleView implements FXComponent {
  Model mod;
  AlternateMvcController cont;

  public PuzzleView(Model mod, AlternateMvcController controller) {
    this.mod = mod;
    this.cont = controller;
  }

  @Override
  public Parent render() {
    GridPane gp = new GridPane();
    for (int i = 0; i < mod.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < mod.getActivePuzzle().getWidth(); j++) {
        Pane p = new Pane();
        p.setMinSize(50, 50);
        p.setMaxHeight(50);
        p.setMaxWidth(50);

        if (mod.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          Button but = new Button("");
          but.setPrefSize(48, 48);
          int r = i;
          int c = j;
          but.setOnAction(
              (ActionEvent event) -> {
                cont.clickCell(r, c);
              });
          p.getChildren().add(but);
          if (mod.isLamp(r, c)) {
            Image bulb = new Image("light-bulb.png");
            ImageView iv = new ImageView(bulb);
            iv.setPreserveRatio(true);
            iv.setFitHeight(20);
            iv.setFitWidth(20);
            if (mod.isLampIllegal(r, c)) {
              but.setStyle("-fx-background-color: red;");
            } else {
              but.setStyle("-fx-background-color: yellow;");
            }
            but.setGraphic(iv);
          } else if (mod.isLit(r, c)) {
            but.setStyle("-fx-background-color: yellow;");
          }
          gp.add(p, r, c);
        }
        if (mod.getActivePuzzle().getCellType(i, j) == CellType.WALL) {
          p.setStyle("-fx-background-color: black;");
          gp.add(p, i, j);
        }
        if (mod.getActivePuzzle().getCellType(i, j) == CellType.CLUE) {
          Label lab = new Label(String.valueOf(mod.getActivePuzzle().getClue(i, j)));
          lab.setMinSize(20, 20);
          p.getChildren().add(lab);
          if (mod.isClueSatisfied(i, j)) {
            p.setStyle("-fx-background-color: green;");
          }
          gp.add(p, i, j);
        }
      }
    }
    return gp;
  }
}
