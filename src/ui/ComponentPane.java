package ui;

import Item.AttackItem;
import Item.CompanionItem;
import Item.Item;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.GameLogic;

class ComponentPane extends VBox {
  private Item item;
  private Label levelLabel;
  private Label costLabel;

  public ComponentPane(Item item) {
    this.item = item;

    // UI elements
    Label nameLabel = new Label(item.toString());
    Rectangle itemBox = new Rectangle(50, 50, Color.LIGHTGRAY);
    levelLabel = new Label("Level: " + item.getLevelItem());
    levelLabel
        .textProperty()
        .bind(item.levelProperty().asString("Level: %d"));

    // Cost Label
    costLabel = new Label();
    costLabel
        .textProperty()
        .bind(Bindings.format("Cost: %d", item.getCostItem()));

    // Style based on croissant count
    ObjectBinding<Color> textColorBinding =
        Bindings.createObjectBinding(
            () ->
                GameLogic.getCroissantCount().get() >= item.getCostItem()
                        .get()
                    ? Color.BLACK
                    : Color.RED,
            GameLogic.getCroissantCount(),
            item.getCostItem()); // Depend on both croissant count and item
                                      // cost

    costLabel.textFillProperty().bind(textColorBinding);

    Button upgradeButton = new Button("Upgrade");

    upgradeButton.setOnAction(
        e -> {
          if (GameLogic.getCroissantCount().get() >= item.getCostItem().get()) {
            GameLogic.setCroissantCount(GameLogic.getCroissantCount().get() - item.getCostItem().get());
            item.upgrade();
          }
		  if(item instanceof AttackItem) {
			  GameLogic.setAttackPerClick();
		  }
		  if(item instanceof CompanionItem) {
			  GameLogic.setDamagePerSec();
		  }
        });

    HBox topLayout = new HBox(10, itemBox, nameLabel);
    topLayout.setAlignment(Pos.CENTER);

    this.getChildren().addAll(topLayout, levelLabel, costLabel, upgradeButton);
    this.setAlignment(Pos.CENTER);
    this.setPadding(new Insets(10));
    this.setSpacing(5);
    this.setStyle("-fx-border-color: black;");
  }
}
