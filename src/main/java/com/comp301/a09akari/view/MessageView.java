package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MessageView implements FXComponent {
  Model mod;
  AlternateMvcController c;

  public MessageView(Model m, AlternateMvcController controller) {
    this.mod = m;
    this.c = controller;
  }

  @Override
  public Parent render() {
    Pane p = new Pane();
    if (mod.isSolved()) {
      Label l = new Label("Congratulations you did it!!");
      p.getChildren().add(l);
    } else {
      Label l = new Label("Keep trying friend");
      p.getChildren().add(l);
    }

    return p;
  }
}
