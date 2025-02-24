package ui;

import java.util.ArrayList;

import card.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;

public class CardEquipmentScene extends BaseScene {

    private HBox slotsContainer;
    private Tooltip arrayTooltips[] = new Tooltip[4];

    public CardEquipmentScene() {
        super();

        Label title = new Label("Equipment");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        slotsContainer = new HBox(20);
        slotsContainer.setAlignment(Pos.CENTER);
        slotsContainer.setPadding(new Insets(20));

        // Create 4 slot views
        for (int i = 0; i < 4; i++) {
        	VBox slotPane = createSlotPane(i);
            slotsContainer.getChildren().add(slotPane);
        }

        VBox layout = new VBox(20, title, slotsContainer);
        layout.setAlignment(Pos.CENTER);

        switchBody(layout);
    }

    private VBox createSlotPane(int slotIndex) {
        VBox slotPane = new VBox();
        slotPane.setPrefSize(100, 140); // size for the card slot
        slotPane.setStyle("-fx-border-color: black; -fx-border-width: 2; "
                        + "-fx-alignment: top_center; -fx-background-color: #eeeeee;");

        updateSlotPane(slotPane, slotIndex);

        // When user clicks this slot, open the CardInventoryScene
        slotPane.setOnMouseClicked(e -> {
            CardInventoryScene.setTargetSlotIndex(slotIndex);
            SceneManager.switchTo("CARD_INVENTORY");
        });

        arrayTooltips[slotIndex] = new Tooltip("No card equipped");
        Tooltip.install(slotPane, arrayTooltips[slotIndex]);

        return slotPane;
    }

    private void updateSlotPane(VBox slotPane, int slotIndex) {
        BaseCard card = GameLogic.getEquippedCards()[slotIndex];
        slotPane.getChildren().clear();

        if (card == null) {
            // Show a plus sign if no card
        	System.out.println("sun");
            Label plusLabel = new Label("+");
            plusLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: gray;");
            StackPane stackPlus = new StackPane(plusLabel);
            
            stackPlus.setAlignment(Pos.CENTER);
                        
            slotPane.getChildren().add(stackPlus);
        } else {
            // Show card name or tier, or an image if you have it
        	
            Label cardLabel = new Label(card.getName() + "\n[" + card.getTier() + "]");
            cardLabel.setStyle("-fx-text-alignment: center; -fx-font-size: 8;");
            ImageView imgView = new ImageView(new Image(card.getCardURL()));
            imgView.setFitWidth(80);
            imgView.setFitHeight(100);
            imgView.setPreserveRatio(true);
          slotPane.setStyle("-fx-border-color: black; -fx-border-width: 2; "
          + "-fx-alignment: top_center; -fx-background-color: #eeeeee;"
          + "-fx-border-color: " + card.getTierStyle()  + ";");
            

            slotPane.getChildren().addAll(cardLabel,imgView);
            
            // Create a Tooltip and attach it to the slot

            // Update the tooltip text dynamically
           
        	System.out.println("sun");
            arrayTooltips[slotIndex].setText("Name: " + card.getName() + "\nTier: " + card.getTier() + 
                            "\nDetail: " + card.toString());
            
        }
    }

    // You can call this when returning from inventory to refresh slot visuals
    public void refreshSlots() {
        for (int i = 0; i < slotsContainer.getChildren().size(); i++) {
        	VBox slotPane = (VBox) slotsContainer.getChildren().get(i);
            updateSlotPane(slotPane, i);
        }
    }
}