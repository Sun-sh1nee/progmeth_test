package ui;

import Item.Item;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;
import player.Player;

public class UpgradeScene extends BaseScene {

  public UpgradeScene() {
    super();

    Player player = GameLogic.getPlayer();

    Item attackItem = player.getItems().get(0);
    Item critRateItem = player.getItems().get(1);
    Item critDamageItem = player.getItems().get(2);
    Item chanceToDropItem = player.getItems().get(3);
    Item companionItem = player.getItems().get(4);

    ComponentPane componentPane1 = new ComponentPane(attackItem);
    ComponentPane componentPane2 = new ComponentPane(critRateItem);
    ComponentPane componentPane3 = new ComponentPane(critDamageItem);
    ComponentPane componentPane4 = new ComponentPane(chanceToDropItem);
    ComponentPane componentPane5 = new ComponentPane(companionItem);

    VBox upgradeLayout =
        new VBox(
            10, componentPane1, componentPane2, componentPane3, componentPane4, componentPane5);
    upgradeLayout.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane(upgradeLayout);
    scrollPane.setFitToWidth(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    switchBody(scrollPane);
  }
}
