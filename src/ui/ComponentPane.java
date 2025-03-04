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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.GameLogic;

class ComponentPane extends VBox {
	private Label levelLabel;
	private Label costLabel;

	public ComponentPane(Item item) {

   
    Label nameLabel = new Label(item.toString());
    ImageView itemImage = new ImageView(new Image(item.getItemURL()));
    itemImage.setFitWidth(50);
    itemImage.setFitHeight(50);
    itemImage.setPreserveRatio(true);

    Rectangle itemBox = new Rectangle(50, 50);
    itemBox.setArcWidth(20); 
    itemBox.setArcHeight(20);
    itemImage.setClip(itemBox);
    
    Rectangle border = new Rectangle(50, 50);
    border.setArcWidth(20);
    border.setArcHeight(20);
    border.setStroke(Color.WHEAT);
    border.setStrokeWidth(5); 
    border.setFill(Color.TRANSPARENT); 
    
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(border, itemImage);
    
    levelLabel = new Label("Level: " + item.getLevelItem());
    levelLabel
        .textProperty()
        .bind(item.levelProperty().asString("Level: %d"));

    
    costLabel = new Label();
    costLabel
        .textProperty()
        .bind(Bindings.format("Cost: %d", item.getCostItem()));

    
    ObjectBinding<Color> textColorBinding =
        Bindings.createObjectBinding(
            () ->
                GameLogic.getCroissantCount().get() >= item.getCostItem()
                        .get()
                    ? Color.BLACK
                    : Color.RED,
            GameLogic.getCroissantCount(),
            item.getCostItem()); 

    costLabel.textFillProperty().bind(textColorBinding);

    Button upgradeButton = new Button("Upgrade");
    upgradeButton.setStyle(
	        "-fx-background-color: Aquamarine;" + 
	        "-fx-border-radius: 10px;" + 
	        "-fx-background-radius: 10px;"
    );

    upgradeButton.setOnMousePressed(e -> upgradeButton.setStyle(
    		"-fx-background-color: LightGreen;" +
    		"-fx-border-radius: 10px;" + 
    		"-fx-background-radius: 10px;"
    ));

    upgradeButton.setOnMouseReleased(e -> upgradeButton.setStyle(
    		"-fx-background-color: Aquamarine;" + 
    		"-fx-border-radius: 10px;" + 
            "-fx-background-radius: 10px;"
    ));

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

    HBox topLayout = new HBox(10, stackPane, nameLabel);
    topLayout.setAlignment(Pos.CENTER);

    this.getChildren().addAll(topLayout, levelLabel, costLabel, upgradeButton);
    this.setAlignment(Pos.CENTER);
    this.setPadding(new Insets(10));
    this.setSpacing(5);
    this.setStyle("-fx-border-color: FFB712; -fx-background-color: FCF4D0;");
  }
}
