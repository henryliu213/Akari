package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BigView implements FXComponent {
  private Model mod;
  private AlternateMvcController controller;

  BigView(Model m, AlternateMvcController controller) {
    this.controller = controller;
    this.mod = m;
  }

  @Override
  public Parent render() {
    Pane p = new VBox();
    PuzzleView pv = new PuzzleView(mod, controller);
    ControlView cv = new ControlView(mod, controller);
    MessageView mv = new MessageView(mod, controller);
    p.getChildren().add(mv.render());
    p.getChildren().add(pv.render());
    p.getChildren().add(cv.render());

    return p;
  }
}
